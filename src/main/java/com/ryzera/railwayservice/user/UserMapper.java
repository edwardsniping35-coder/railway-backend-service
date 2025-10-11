package com.ryzera.railwayservice.user;

import com.ryzera.railwayservice.parcel.dto.ParcelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ParcelDto.UserDto toUserDto(UserEntity userEntity);
}