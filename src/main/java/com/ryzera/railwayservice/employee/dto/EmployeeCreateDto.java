package com.ryzera.railwayservice.employee.dto;

import com.ryzera.railwayservice.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

@Builder
public record EmployeeCreateDto(
        // Fields for EmployeeProfile
        @NotBlank @Size(max = 50)
        String employeeId,

        @NotBlank @Size(max = 100)
        String firstName,

        @NotBlank @Size(max = 100)
        String lastName,

        @NotBlank @Size(min = 10, max = 15)
        String nicNumber,

        Long assignedStationId, // Optional: The ID of the station they are assigned to

        // Fields for the central User account
        @NotBlank @Size(min = 10, max = 10)
        String mobileNumber,

        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotNull
        UserStatus status,

        @NotEmpty(message = "Employee must be assigned at least one role.")
        Set<Long> roleIds

) {
}