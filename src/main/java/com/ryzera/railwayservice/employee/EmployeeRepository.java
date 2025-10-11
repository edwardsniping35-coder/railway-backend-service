package com.ryzera.railwayservice.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByEmployeeId(String employeeId);
    Optional<EmployeeEntity> findByNicNumber(String nicNumber);
    // We already have findByWorkEmail in the old version, which can be removed or repurposed
}