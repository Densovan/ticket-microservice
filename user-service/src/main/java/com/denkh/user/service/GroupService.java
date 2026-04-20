package com.denkh.user.service;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.CreateGroupRequest;
import com.denkh.user.dto.request.GroupMemberRequest;




public interface GroupService {

    ResponseErrorTemplate createGroup(CreateGroupRequest request);

    ResponseErrorTemplate updateGroup(Long id, CreateGroupRequest request);

    ResponseErrorTemplate addMembersToGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    ResponseErrorTemplate removeMembersFromGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    ResponseErrorTemplate getGroupMembers(Long groupId);
}