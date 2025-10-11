package com.ryzera.railwayservice.routes.dto;

import com.ryzera.railwayservice.enums.RouteStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.List;

// This file now contains all DTOs related to Routes
public class RouteDto {

    // For creating/updating a single stop in a route
    public record RouteStopDto(
            @NotNull Long stationId,
            @NotNull @Positive Integer stopOrder,
            OffsetDateTime scheduledArrivalTime,
            OffsetDateTime scheduledDepartureTime,
            Double distanceFromOriginKm
    ) {}

    // For creating a new route
    public record RouteCreateDto(
            @NotBlank String routeName,
            @NotBlank String routeNumber,
            @NotNull Long trainId,
            @NotNull Long originStationId,
            @NotNull Long destinationStationId,
            @NotNull RouteStatus status,
            @Valid @NotEmpty List<RouteStopDto> stops
    ) {}

    // For updating an existing route
    public record RouteUpdateDto(
            @NotBlank String routeName,
            @NotNull Long trainId,
            @NotNull Long originStationId,
            @NotNull Long destinationStationId,
            @NotNull RouteStatus status,
            @Valid @NotEmpty List<RouteStopDto> stops
    ) {}

    // For API responses
    public record RouteStopResponseDto(
            Long id, Long stationId, String stationName, Integer stopOrder,
            OffsetDateTime scheduledArrivalTime, OffsetDateTime scheduledDepartureTime, Double distanceFromOriginKm
    ) {}

    public record StationDto(Long id, String name) {}
    public record TrainDto(Long id, String name, String trainId) {}

    public record RouteResponseDto(
            Long id, String routeName, String routeNumber, RouteStatus status,
            TrainDto train, StationDto originStation, StationDto destinationStation,
            List<RouteStopResponseDto> stops, OffsetDateTime createdAt
    ) {}
}