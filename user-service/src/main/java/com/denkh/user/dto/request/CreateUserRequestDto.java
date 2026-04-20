package com.denkh.user.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("user_image")
    private String userImage;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("user_type")
    private String userType;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    @JsonProperty("last_login")
    private String lastLogin;
    @JsonProperty("login_attempt")
    private Integer loginAttempt;
    @JsonProperty("max_attempt")
    private Integer maxAttempt;
    @JsonProperty("enable_allocate")
    private Boolean enableAllocate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("roles")
    private Set<String> roles;
    @JsonProperty("groups")
    private Set<String> groups;
}
