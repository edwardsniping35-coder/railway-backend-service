package com.ryzera.railwayservice.parcel.entity;

import com.ryzera.railwayservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a single item within a parcel.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcel_item")
@SuperBuilder(toBuilder = true)
public class ParcelItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_id", nullable = false)
    private ParcelEntity parcel;

    @Column(nullable = false)
    private String type; // e.g., "Electronics", "Documents"

    private String category;

    @Column(nullable = false)
    private Double weightInKg;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal fee;

}
