package com.ryzera.railwayservice.train.dto;

import com.ryzera.railwayservice.enums.TrainStatus;
import com.ryzera.railwayservice.enums.TrainType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new Train.
 * This record carries the data from the API request to the service layer.
 */
public record TrainCreateDto(

        /**
         * The public name of the train (e.g., "Udarata Menike").
         */
        @NotBlank(message = "Train name cannot be blank")
        @Size(max = 255, message = "Train name must not exceed 255 characters")
        String name,

        /**
         * The unique business identifier for the train (e.g., "1015").
         */
        @NotBlank(message = "Train ID cannot be blank")
        @Size(max = 50, message = "Train ID must not exceed 50 characters")
        String trainId,

        /**
         * The type of the train.
         */
        @NotNull(message = "Train type must be specified")
        TrainType type,

        /**
         * The initial status of the train upon creation.
         */
        @NotNull(message = "Train status must be specified")
        TrainStatus status
) {
}
