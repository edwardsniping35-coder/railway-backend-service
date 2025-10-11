package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.routes.dto.RouteDto;
import com.ryzera.railwayservice.stations.StationMapper;
import com.ryzera.railwayservice.train.TrainMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RouteStopMapper.class, TrainMapper.class, StationMapper.class})
public interface RouteMapper {

    @Mapping(source = "train", target = "train")
    @Mapping(source = "originStation", target = "originStation")
    @Mapping(source = "destinationStation", target = "destinationStation")
    RouteDto.RouteResponseDto toDto(RouteEntity routeEntity);
}