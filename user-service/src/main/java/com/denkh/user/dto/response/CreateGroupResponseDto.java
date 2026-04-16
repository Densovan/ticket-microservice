package com.denkh.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupResponseDto {
    Long id;
    String name;
    String description;
    Set<String> roles;
    Set<String> permissions;
    String status;
    String createdBy;
    String updatedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    int memberCount;
}
