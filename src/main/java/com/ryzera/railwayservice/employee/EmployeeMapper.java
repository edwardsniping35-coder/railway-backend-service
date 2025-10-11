package com.ryzera.railwayservice.employee;

import com.ryzera.railwayservice.employee.dto.EmployeeCreateDto;
import com.ryzera.railwayservice.employee.dto.EmployeeResponseDto;
import com.ryzera.railwayservice.employee.dto.EmployeeUpdateDto; // <-- IMPORT
import com.ryzera.railwayservice.role.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.mobileNumber", target = "mobileNumber")
    @Mapping(source = "user.status", target = "status")
    @Mapping(source = "user.roles", target = "roles")
    @Mapping(source = "assignedStation.id", target = "assignedStationId")
    EmployeeResponseDto toDto(EmployeeEntity employeeEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignedStation", ignore = true)
        // ... other ignore mappings ...
    EmployeeEntity toEntity(EmployeeCreateDto createDto);

    // VVV-- ADD THIS NEW METHOD --VVV
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignedStation", ignore = true)
    @Mapping(target = "employeeId", ignore = true) // employeeId should be immutable
    @Mapping(target = "nicNumber", ignore = true) // nicNumber should be immutable
    // ... other ignore mappings ...
    void updateProfileFromDto(EmployeeUpdateDto updateDto, @MappingTarget EmployeeEntity employeeEntity);
}