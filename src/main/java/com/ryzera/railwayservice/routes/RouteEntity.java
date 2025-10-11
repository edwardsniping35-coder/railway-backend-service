package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.RouteStatus;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.train.TrainEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route")
@SuperBuilder(toBuilder = true)
public class RouteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String routeName;

    @Column(unique = true, nullable = false, length = 50)
    private String routeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private TrainEntity train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_station_id", nullable = false)
    private StationEntity originStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private StationEntity destinationStation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteStatus status;

    @OneToMany(
            mappedBy = "route",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("stopOrder ASC") // Ensures the list of stops is always sorted
    private List<RouteStopEntity> stops = new ArrayList<>();

}
