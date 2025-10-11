package com.ryzera.railwayservice.employee.dto;

import com.ryzera.railwayservice.enums.CommonActiveStatus;
import com.ryzera.railwayservice.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record EmployeeUpdateDto(
        @NotBlank @Size(max = 100)
        String firstName,

        @NotBlank @Size(max = 100)
        String lastName,

        // The user's status (e.g., ACTIVE, INACTIVE)
        @NotNull
        UserStatus status,

        // The full set of role IDs the user should now have
        @NotEmpty(message = "Employee must be assigned at least one role.")
        Set<Long> roleIds,

        // The ID of the station the employee is assigned to (can be null)
        Long assignedStationId
) {
}