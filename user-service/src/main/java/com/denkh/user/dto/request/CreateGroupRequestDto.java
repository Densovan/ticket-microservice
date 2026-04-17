package com.denkh.user.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreateGroupRequestDto {
//    private Long id;
    private String name;
    private String description;
    private Set<String> roles;
    private Set<String> permissions;
    private String status;
    private boolean active;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private int memberCount;
}
