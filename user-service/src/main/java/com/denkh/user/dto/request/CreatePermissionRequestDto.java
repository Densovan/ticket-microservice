package com.denkh.user.dto.request;

import com.denkh.user.entity.Group;
import com.denkh.user.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreatePermissionRequestDto {
    private String name;
    private String description;
    private String status;
    private Set<Role> roles;
    private Set<Group> groups;
}
