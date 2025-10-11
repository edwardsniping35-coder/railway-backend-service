package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.routes.dto.RouteStopResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteStopMapper {
    @Mapping(source = "station.id", target = "stationId")
    @Mapping(source = "station.name", target = "stationName")
    RouteStopResponseDto toDto(RouteStopEntity routeStopEntity);
}