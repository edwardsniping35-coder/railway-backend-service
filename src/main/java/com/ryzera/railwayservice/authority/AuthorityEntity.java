package com.ryzera.railwayservice.authority;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.CommonActiveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
@SuperBuilder(toBuilder = true)
public class AuthorityEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;//Manage Parcels

    private String code;//man-par

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    private CommonActiveStatus status;//ACTIVE

}
