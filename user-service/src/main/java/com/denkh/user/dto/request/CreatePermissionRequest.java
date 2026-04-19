package com.denkh.user.dto.request;

import com.denkh.user.entity.Group;
import com.denkh.user.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;


public record CreatePermissionRequest(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("status") String status,
        @JsonProperty("roles") Set<Role> roles,
        @JsonProperty("groups") Set<Group> groups
) {
}
