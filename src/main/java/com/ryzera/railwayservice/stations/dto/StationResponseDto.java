package com.ryzera.railwayservice.stations.dto;

import com.ryzera.railwayservice.enums.CommonActiveStatus;
import java.time.OffsetDateTime;

public record StationResponseDto(
        Long id,
        String name,
        String code,
        String address,
        CommonActiveStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime lastModifiedAt,
        Long createdUserId,
        Long lastModifiedUserId
) {
}