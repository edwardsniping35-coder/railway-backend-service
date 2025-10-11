package com.ryzera.railwayservice.employee.dto;

import com.ryzera.railwayservice.enums.CommonActiveStatus;
import com.ryzera.railwayservice.enums.UserStatus;
import com.ryzera.railwayservice.role.dto.RoleResponseDto;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;

@Builder
public record EmployeeResponseDto(
        Long id,
        String employeeId,
        String firstName,
        String lastName,
        String nicNumber,
        Long assignedStationId,

        // Fields from the associated UserEntity
        Long userId,
        String mobileNumber,
        UserStatus status,
        Set<RoleResponseDto> roles,

        OffsetDateTime createdAt
) {
}