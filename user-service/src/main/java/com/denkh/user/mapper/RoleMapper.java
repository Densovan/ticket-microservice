package com.denkh.user.mapper;

import com.denkh.user.dto.request.CreateRoleRequestDto;
import com.denkh.user.dto.response.CreateRoleResponseDto;
import com.denkh.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role toRole(CreateRoleRequestDto createRoleRequestDto);

    CreateRoleResponseDto toCreateRoleResponseDto(Role role);
}
