package com.ryzera.railwayservice.stations.dto;

import com.ryzera.railwayservice.enums.CommonActiveStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StationCreateDto(
        @NotBlank(message = "Station name cannot be blank")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Station code cannot be blank")
        @Size(max = 50, message = "Station code must not exceed 50 characters")
        String code,

        @Size(max = 500)
        String address,

        @NotNull(message = "Status must be specified")
        CommonActiveStatus status
) {
}