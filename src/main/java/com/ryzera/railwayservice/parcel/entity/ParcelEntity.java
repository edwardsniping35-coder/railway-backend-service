package com.ryzera.railwayservice.parcel.entity;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.ParcelStatus;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.train.TrainEntity;
import com.ryzera.railwayservice.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcel")
@SuperBuilder(toBuilder = true)
public class ParcelEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_station_id", nullable = false)
    private StationEntity originStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private StationEntity destinationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_id", nullable = false)
    private UserEntity senderUser;

    // --- THIS IS THE CRITICAL CHANGE ---
    // This is now the employee who VERIFIES the parcel. It is null on creation.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_user_id") // <-- Now nullable
    private UserEntity processedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    private TrainEntity train;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelStatus status;

    @Column(nullable = false)
    private Double totalWeightInKg;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private String description;

    @OneToMany(
            mappedBy = "parcel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ParcelItemEntity> items = new ArrayList<>();
}

