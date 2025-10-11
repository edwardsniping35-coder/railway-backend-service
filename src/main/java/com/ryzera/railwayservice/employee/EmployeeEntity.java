package com.ryzera.railwayservice.employee;

import com.ryzera.railwayservice.entity.BaseEntity;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "employee_profile")
@SuperBuilder(toBuilder = true)
public class EmployeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-one link to the central user account for this employee
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(unique = true, nullable = false, length = 50)
    private String employeeId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(name = "nic_number", unique = true, nullable = false, length = 15)
    private String nicNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_station_id")
    private StationEntity assignedStation;

}
