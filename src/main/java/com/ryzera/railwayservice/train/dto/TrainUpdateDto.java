package com.ryzera.railwayservice.train.dto;

import com.ryzera.railwayservice.enums.TrainStatus;
import com.ryzera.railwayservice.enums.TrainType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrainUpdateDto(
        @NotBlank(message = "Train name cannot be blank")
        @Size(max = 255)
        String name,

        // Note: We might exclude trainId from an update DTO if it's considered immutable.
        // For this example, we'll allow it to be updated.
        @NotBlank(message = "Train ID cannot be blank")
        @Size(max = 50)
        String trainId,

        @NotNull(message = "Train type must be specified")
        TrainType type,

        @NotNull(message = "Train status must be specified")
        TrainStatus status
) {
}