package com.denkh.user.mapper;

import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.response.AuthResponse;
import com.denkh.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "groups", ignore = true)
    User toUser(CreateUserRequestDto createUserRequestDto);

    AuthResponse toAuthResponse(User user);
}
