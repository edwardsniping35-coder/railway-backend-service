package com.ryzera.railwayservice.routes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    Optional<RouteEntity> findByRouteNumber(String routeNumber);

    // This custom query uses a JOIN FETCH to load the route and its stops in a single database query.
    @Query("SELECT r FROM RouteEntity r LEFT JOIN FETCH r.stops WHERE r.id = :id")
    Optional<RouteEntity> findByIdWithStops(@Param("id") Long id);
}