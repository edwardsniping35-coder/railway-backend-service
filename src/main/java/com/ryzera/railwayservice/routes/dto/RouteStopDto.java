package com.ryzera.railwayservice.routes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.OffsetDateTime;

public record RouteStopDto(
        @NotNull(message = "Station ID for a stop cannot be null")
        Long stationId,

        @NotNull(message = "Stop order cannot be null")
        @Positive(message = "Stop order must be a positive number")
        Integer stopOrder,

        OffsetDateTime
        scheduledArrivalTime,

        OffsetDateTime
        scheduledDepartureTime,

        Double distanceFromOriginKm
) {}