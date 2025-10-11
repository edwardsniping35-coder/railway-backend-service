package com.ryzera.railwayservice.routes.dto;

import java.time.OffsetDateTime;

public record RouteStopResponseDto(
        Long id,
        Long stationId,
        String stationName, // Include name for better UI experience
        Integer stopOrder,
        OffsetDateTime scheduledArrivalTime,
        OffsetDateTime scheduledDepartureTime,
        Double distanceFromOriginKm
) {}