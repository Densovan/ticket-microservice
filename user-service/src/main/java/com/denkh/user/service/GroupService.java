package com.denkh.user.service;

import com.denkh.user.dto.request.CreateGroupRequestDto;
import com.denkh.user.dto.request.GroupMemberRequest;
import com.denkh.user.dto.response.CreateGroupResponseDto;

public interface GroupService {

    CreateGroupResponseDto createGroup(CreateGroupRequestDto request);

    CreateGroupResponseDto updateGroup(Long id, CreateGroupRequestDto request);

    CreateGroupResponseDto addMemberToGroup(Long id,GroupMemberRequest groupMemberRequest);

    CreateGroupResponseDto removeMembersFromGroup(Long id,GroupMemberRequest groupMemberRequest);

}
