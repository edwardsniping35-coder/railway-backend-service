package com.ryzera.railwayservice.parcel;

import com.ryzera.railwayservice.parcel.entity.ParcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    Optional<ParcelEntity> findByTrackingId(String trackingId);
}
