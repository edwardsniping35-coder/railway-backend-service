package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.stations.StationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_stop")
@SuperBuilder(toBuilder = true)
public class RouteStopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private StationEntity station;

    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder; // The sequence number of the stop (1, 2, 3...)

    @Column(name = "scheduled_arrival_time")
    private OffsetDateTime scheduledArrivalTime; // Arrival might be null for the first station

    @Column(name = "scheduled_departure_time")
    private OffsetDateTime scheduledDepartureTime; // Departure might be null for the last station

    @Column(name = "distance_from_origin_km")
    private Double distanceFromOriginKm;

}
