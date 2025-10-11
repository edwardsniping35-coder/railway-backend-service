package com.ryzera.railwayservice.user;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.enums.LoginProvider;
import com.ryzera.railwayservice.enums.UserStatus;
import com.ryzera.railwayservice.role.RoleEntity;
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

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
@SuperBuilder(toBuilder = true)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private UserStatus status;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private Integer failedLoginAttemptCount;

    private OffsetDateTime lastFailedLoginDate;

    private OffsetDateTime lastLoginDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private LoginProvider provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

}
