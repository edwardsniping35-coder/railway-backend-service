package com.ryzera.railwayservice.routes.dto;

import com.ryzera.railwayservice.enums.RouteStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RouteCreateDto(

        @NotBlank String
        routeName,

        @NotBlank String
        routeNumber,

        @NotNull Long
        trainId,

        @NotNull
        Long originStationId,

        @NotNull
        Long destinationStationId,

        @NotNull
        RouteStatus status,

        @Valid // This enables validation on the nested objects
        @NotEmpty(message = "A route must have at least one stop")
        List<RouteStopDto> stops
) {}