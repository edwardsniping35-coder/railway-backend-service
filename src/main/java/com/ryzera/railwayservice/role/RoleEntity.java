package com.ryzera.railwayservice.role;

import com.ryzera.railwayservice.authority.AuthorityEntity;
import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.CommonActiveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@SuperBuilder(toBuilder = true)
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "ROLE_STATION_MASTER", "ROLE_ADMIN"

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private CommonActiveStatus status;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER is often useful for loading all permissions with the role
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<AuthorityEntity> authorities;

}
