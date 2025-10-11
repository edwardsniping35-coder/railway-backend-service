package com.ryzera.railwayservice.train.dto;

import com.ryzera.railwayservice.enums.TrainStatus;
import com.ryzera.railwayservice.enums.TrainType;

import java.time.OffsetDateTime;

/**
 * Data Transfer Object for representing a Train in API responses.
 * This record is used to send train data from the server to the client.
 */
public record TrainResponseDto(

        /**
         * The unique database identifier for the train.
         */
        Long id,

        /**
         * The public name of the train (e.g., "Udarata Menike").
         */
        String name,

        /**
         * The unique business identifier for the train (e.g., "1015").
         */
        String trainId,

        /**
         * The type of the train.
         */
        TrainType type,

        /**
         * The current status of the train.
         */
        TrainStatus status,

        /**
         * The timestamp of when the record was created.
         */
        OffsetDateTime createdAt,

        /**
         * The timestamp of the last modification.
         */
        OffsetDateTime lastModifiedAt,

        /**
         * The ID of the user who created the record.
         */
        Long createdUserId,

        /**
         * The ID of the user who last modified the record.
         */
        Long lastModifiedUserId
) {
}