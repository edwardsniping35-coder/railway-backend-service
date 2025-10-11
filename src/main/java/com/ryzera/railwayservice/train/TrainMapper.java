package com.ryzera.railwayservice.train;

import com.ryzera.railwayservice.parcel.dto.ParcelDto;
import com.ryzera.railwayservice.train.dto.TrainCreateDto;
import com.ryzera.railwayservice.train.dto.TrainResponseDto;
import com.ryzera.railwayservice.train.dto.TrainUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TrainMapper {

    TrainResponseDto toDto(TrainEntity trainEntity);

    TrainEntity toEntity(TrainCreateDto createDto);

    // This method updates an existing entity from a DTO
    void updateEntityFromDto(TrainUpdateDto updateDto, @MappingTarget TrainEntity trainEntity);

    ParcelDto.TrainDto toTrainDto(TrainEntity trainEntity);
}
