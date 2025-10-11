package com.ryzera.railwayservice.routes.dto;

import com.ryzera.railwayservice.enums.RouteStatus;
import java.time.OffsetDateTime;
import java.util.List;

public record RouteResponseDto(
        Long id,
        String routeName,
        String routeNumber,
        Long trainId,
        String trainName,
        Long originStationId,
        String originStationName,
        Long destinationStationId,
        String destinationStationName,
        RouteStatus status,
        List<RouteStopResponseDto> stops,
        OffsetDateTime createdAt
) {}