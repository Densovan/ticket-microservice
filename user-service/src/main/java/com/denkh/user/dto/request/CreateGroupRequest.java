package com.denkh.user.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupRequest {

    String name;
    String description;
    Set<String> roles;
    Set<String> permissions;
    String status;
}
