package com.denkh.user.service.impl;

import com.denkh.common.constant.ApiConstant;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreateGroupRequest;
import com.denkh.user.dto.request.GroupMemberRequest;
import com.denkh.user.dto.response.GroupResponse;
import com.denkh.user.entity.Group;
import com.denkh.user.entity.Permission;
import com.denkh.user.entity.Role;
import com.denkh.user.entity.User;
import com.denkh.user.repository.GroupRepository;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.repository.UserRepository;
import com.denkh.user.service.GroupService;
import com.denkh.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PermissionService permissionService;


    @Override
    public ResponseErrorTemplate createGroup(CreateGroupRequest request) {
        if (groupRepository.existsByName(request.getName())) {
            throw new RuntimeException("Group with name '" + request.getName() + "' already exists");
        }

        Set<Role> roles = request.getRoles() != null
                ? new HashSet<>(roleRepository.findByNameIn(request.getRoles()))
                : Set.of();
        Set<Permission> permissions = request.getPermissions() != null
                ? new HashSet<>(permissionService.getPermissionsByNameIn(request.getPermissions()))
                : Set.of();

        Group group = mapToGroup(request);
        group.setRoles(roles);
        group.setPermissions(permissions);

        groupRepository.save(group);
        return constructGroupResponse(group);
    }

    @Override
    public ResponseErrorTemplate updateGroup(Long groupId, CreateGroupRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        final String groupName = request.getName();
        if (groupName != null && !groupName.equals(group.getName())) {
            if (groupRepository.existsByName(groupName)) {
                throw new RuntimeException("Group with name '" + groupName + "' already exists");
            }
            group.setName(groupName);
        }

        if (request.getDescription() != null) {
            group.setDescription(request.getDescription());
        }

        if (request.getRoles() != null) {
            Set<Role> roles = new HashSet<>(roleRepository.findByNameIn(request.getRoles()));
            group.setRoles(roles);
        }

        if (request.getPermissions() != null) {
            Set<Permission> permissions = new HashSet<>(permissionService.getPermissionsByNameIn(request.getPermissions()));
            group.setPermissions(permissions);
        }

        if (StringUtils.hasText(request.getStatus())) {
            group.setStatus(request.getStatus());
        }
        groupRepository.save(group);

        return constructGroupResponse(group);
    }

    @Override
    public ResponseErrorTemplate addMembersToGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        List<User> users = userRepository.findAllById(groupMemberRequest.getUserIds());

        for (User user : users) {
            if (!group.getUsers().contains(user)) {
                group.addUser(user);
            }
        }

        groupRepository.save(group);

        return constructGroupResponse(group);
    }

    @Override
    public ResponseErrorTemplate removeMembersFromGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        List<User> users = userRepository.findAllById(groupMemberRequest.getUserIds());

        for (User user : users) {
            group.removeUser(user);
        }

        Group updatedGroup = groupRepository.save(group);
        return constructGroupResponse(updatedGroup);
    }


    private ResponseErrorTemplate constructGroupResponse(Group group) {
        GroupResponse groupResponse = new  GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toSet()),
                group.getPermissions().stream().map(Permission::getName).collect(java.util.stream.Collectors.toSet()),
                group.getStatus(),
                group.getCreatedBy(),
                group.getUpdatedBy(),
                group.getCreatedAt(),
                group.getUpdatedAt(),
                group.getUsers().size()
        );
        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                groupResponse,
                false
        );

    }

    private Group mapToGroup(CreateGroupRequest request) {
        Group group = new Group();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setStatus(Constant.ACTIVE);

        return group;
    }

}
