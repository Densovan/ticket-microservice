package com.denkh.user.service.impl;


import com.denkh.common.constant.ApiConstant;
import com.denkh.common.dto.EmptyObject;
import com.denkh.common.exception.CustomMessageException;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.config.properties.JwtConfigProperties;
import com.denkh.user.entity.CustomUserDetail;
import com.denkh.user.entity.RefreshToken;
import com.denkh.user.entity.User;
import com.denkh.user.jwt.JwtSecret;
import com.denkh.user.repository.RefreshTokenRepository;
import com.denkh.user.repository.UserRepository;
import com.denkh.user.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl extends JwtConfigProperties implements JwtService {

    private final JwtSecret jwtSecret;
    //    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailService userDetailService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        return jwtSecret.getSecretKey(secret);
    }

    @Override
    public String generateToken(CustomUserDetail customUserDetail) {
        List<String> roles = new ArrayList<>();

        customUserDetail.getAuthorities().forEach(role -> {
            roles.add(role.getAuthority());
        });

        Instant currentTime = Instant.now();
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .claim("authorities", customUserDetail.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("roles", roles)
                .claim("isEnable", customUserDetail.isEnabled())
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(Date.from(currentTime.plusSeconds(getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public String refreshToken(CustomUserDetail customUserDetail) {

        Instant currentTime = Instant.now();
        var tokenExpiration = Date.from(currentTime.plusMillis(600000));
        var refreshToken = Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(tokenExpiration)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        createRefreshToken(customUserDetail.getUsername(), refreshToken, tokenExpiration);
        return refreshToken;
    }

    @Override
    public boolean isValidToken(String token) {
        final String username = extractUsername(token);

        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return userDetails != null;
    }

    @Override
    public ResponseErrorTemplate verifyToken(String authorizationHeader) {
        try {
            // Remove "Bearer " prefix if present
            String token = authorizationHeader;
            if (authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            // Verify token validity
            if (!isValidToken(token)) {
                return new ResponseErrorTemplate(
                        "Invalid token",
                        "TOKEN_INVALID",
                        new EmptyObject(),
                        true);
            }

            // Extract claims
            Claims claims = extractClaims(token);
            String username = claims.getSubject();
            List authorities = claims.get("authorities", List.class);

            // Create response data
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("username", username);
            tokenData.put("authorities", authorities);
            tokenData.put("valid", true);

            return new ResponseErrorTemplate(
                    "Token is valid",
                    "TOKEN_VALID",
                    tokenData,
                    false);

        } catch (Exception ex) {
            log.error("Token verification failed: {}", ex.getMessage());
            return new ResponseErrorTemplate(
                    "Token verification failed",
                    "TOKEN_VERIFICATION_FAILED",
                    new EmptyObject(),
                    true);
        }
    }

    private String extractUsername(String token) {
        return extractClaimsTFunction(token, Claims::getSubject);
    }

    private <T> T extractClaimsTFunction(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CustomMessageException(
                    "Token expiration",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CustomMessageException(
                    "Token is not support.",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException | SignatureException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CustomMessageException(
                    "Token is invalid format.",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            throw new CustomMessageException(
                    ex.getLocalizedMessage(),
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private void createRefreshToken(String username, String token, Date tokenExpiration) {
        Optional<User> user = userRepository.findFirstByUsernameAndStatus(username, ApiConstant.ACTIVE.getKey());
        if (user.isEmpty()) {
            log.error("User not found with username: {}", username);
            throw new CustomMessageException(
                    ApiConstant.USER_NAME_NOT_FOUND.getDescription(),
                    ApiConstant.USER_NAME_NOT_FOUND.getKey(),
                    new EmptyObject(),
                    HttpStatus.NOT_FOUND);
        }

        RefreshToken refreshToken;
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUser(user.get());
        if (refreshTokenOptional.isPresent()) {
            log.info("Refresh refreshToken already exists..!");
            refreshToken = refreshTokenOptional.get();
            refreshToken.setToken(token);
            refreshToken.setExpiryDate(tokenExpiration);

        } else {
            refreshToken = RefreshToken.builder()
                    .user(user.get())
                    .token(token)
                    .expiryDate(tokenExpiration)
                    .build();
        }
        log.info("Refresh refreshToken created successfully..!");
        refreshTokenRepository.save(refreshToken);
    }


}