package com.ryzera.railwayservice.train;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.TrainStatus;
import com.ryzera.railwayservice.enums.TrainType;
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
@Table(name = "train")
@SuperBuilder(toBuilder = true)
public class TrainEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String trainId;

    @Enumerated(EnumType.STRING)
    private TrainType type;

    @Enumerated(EnumType.STRING)
    private TrainStatus status;

}
