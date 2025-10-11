package com.ryzera.railwayservice.parcel;

import com.ryzera.railwayservice.parcel.dto.ParcelDto;
import com.ryzera.railwayservice.parcel.entity.ParcelEntity;
import com.ryzera.railwayservice.parcel.entity.ParcelItemEntity; // <-- IMPORT
import com.ryzera.railwayservice.stations.StationMapper;
import com.ryzera.railwayservice.train.TrainMapper;
import com.ryzera.railwayservice.user.UserMapper;
import org.mapstruct.Mapper;

// --- THIS IS THE CRITICAL FIX ---
// Tell this mapper that it can use the other mappers for nested conversions.
@Mapper(componentModel = "spring", uses = {StationMapper.class, UserMapper.class, TrainMapper.class})
public interface ParcelMapper {

    // MapStruct will now automatically use the helper mappers for the nested objects.
    // The explicit @Mapping annotations are no longer needed for them.
    ParcelDto.ParcelResponseDto toDto(ParcelEntity parcelEntity);

    ParcelDto.ParcelItemResponseDto toItemDto(ParcelItemEntity parcelItemEntity);
}