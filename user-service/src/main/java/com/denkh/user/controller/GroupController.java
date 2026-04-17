package com.denkh.user.controller;

import com.denkh.common.dto.ApiResponse;
import com.denkh.user.dto.request.CreateGroupRequestDto;
import com.denkh.user.dto.request.GroupMemberRequest;
import com.denkh.user.dto.response.CreateGroupResponseDto;
import com.denkh.user.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @PostMapping
    public ResponseEntity<ApiResponse<CreateGroupResponseDto>> createGroup(@Valid @RequestBody CreateGroupRequestDto request) {
        return ResponseEntity.ok(ApiResponse.success("Group created successfully", groupService.createGroup(request)));

    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<CreateGroupResponseDto>> updateGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody CreateGroupRequestDto request) {
        return ResponseEntity.ok(ApiResponse.success("Group updated successfully", groupService.updateGroup(groupId, request)));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<CreateGroupResponseDto>> addMembersToGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member added successfully", groupService.addMemberToGroup(groupId, request)));
    }

    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<CreateGroupResponseDto>> removeMembersFromGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member removed successfully", groupService.removeMembersFromGroup(groupId, request)));
    }
}
