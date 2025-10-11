package com.ryzera.railwayservice.role;

import com.ryzera.railwayservice.role.dto.RoleResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    /**
     * Maps a RoleEntity to a RoleResponseDto.
     * @param roleEntity The source entity.
     * @return The resulting DTO.
     */
    RoleResponseDto toDto(RoleEntity roleEntity);
}