package com.ryzera.railwayservice.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedBy
    private Long createdUserId;

    @LastModifiedBy
    private Long lastModifiedUserId;

    @CreatedDate
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private OffsetDateTime lastModifiedAt;

}
