package com.ryzera.railwayservice.stations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {

    /**
     * Finds a station by its unique code.
     * @param code The station code to search for.
     * @return An Optional containing the StationEntity if found.
     */
    Optional<StationEntity> findByCode(String code);

}
