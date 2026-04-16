package com.denkh.user.dto.response;

import com.denkh.common.dto.BaseDto;
import com.denkh.user.entity.Permission;
import com.denkh.user.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatePermissionResponseDto extends BaseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    private Set<Role> roles;

    private Set<Permission> permissions;
}
