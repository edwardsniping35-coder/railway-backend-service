package com.ryzera.railwayservice.stations;

import com.ryzera.railwayservice.parcel.dto.ParcelDto;
import com.ryzera.railwayservice.stations.dto.StationCreateDto;
import com.ryzera.railwayservice.stations.dto.StationResponseDto;
import com.ryzera.railwayservice.stations.dto.StationUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StationMapper {

    StationResponseDto toDto(StationEntity stationEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "lastModifiedUserId", ignore = true)
    StationEntity toEntity(StationCreateDto createDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "lastModifiedUserId", ignore = true)
    void updateEntityFromDto(StationUpdateDto updateDto, @MappingTarget StationEntity stationEntity);

    ParcelDto.StationDto toStationDto(StationEntity stationEntity);
}