package com.ryzera.railwayservice.employee;

import com.ryzera.railwayservice.employee.dto.EmployeeCreateDto;
import com.ryzera.railwayservice.employee.dto.EmployeeResponseDto;
import com.ryzera.railwayservice.employee.dto.EmployeeUpdateDto;
import com.ryzera.railwayservice.exception.ConflictException;
import com.ryzera.railwayservice.exception.NotFoundException;
import com.ryzera.railwayservice.role.RoleEntity;
import com.ryzera.railwayservice.role.RoleRepository;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.stations.StationRepository;
import com.ryzera.railwayservice.user.UserEntity;
import com.ryzera.railwayservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StationRepository stationRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateDto createDto) {
        // 1. Check for conflicts
        userRepository.findByMobileNumber(createDto.mobileNumber()).ifPresent(u -> {
            throw new ConflictException("User with mobile number " + createDto.mobileNumber() + " already exists.");
        });
        employeeRepository.findByEmployeeId(createDto.employeeId()).ifPresent(e -> {
            throw new ConflictException("Employee with ID " + createDto.employeeId() + " already exists.");
        });
        employeeRepository.findByNicNumber(createDto.nicNumber()).ifPresent(e -> {
            throw new ConflictException("Employee with NIC " + createDto.nicNumber() + " already exists.");
        });

        // 2. Fetch Roles
        List<RoleEntity> rolesList = roleRepository.findAllById(createDto.roleIds());
        if (rolesList.size() != createDto.roleIds().size()) {
            throw new NotFoundException("One or more specified roles could not be found.");
        }
        Set<RoleEntity> roles = new HashSet<>(rolesList);

        // 3. Create and populate the central UserEntity
        UserEntity newUser = UserEntity
                .builder()
                .status(createDto.status())
                .roles(roles)
                .mobileNumber(createDto.mobileNumber())
                .failedLoginAttemptCount(0)
                .password(passwordEncoder.encode(createDto.password()))
                .build();

        // 4. Create and populate the EmployeeEntity (profile)
        EmployeeEntity newEmployee = employeeMapper.toEntity(createDto);

        // 5. Link the employee profile to the central user account
        newEmployee.setUser(newUser);

        // 6. (Optional) Link the assigned station
        if (createDto.assignedStationId() != null) {
            StationEntity station = stationRepository.findById(createDto.assignedStationId())
                    .orElseThrow(() -> new NotFoundException("Assigned station with ID " + createDto.assignedStationId() + " not found."));
            newEmployee.setAssignedStation(station);
        }

        // 7. Save the EmployeeEntity. Due to CascadeType.ALL, the UserEntity will be saved automatically.
        EmployeeEntity savedEmployee = employeeRepository.save(newEmployee);

        return employeeMapper.toDto(savedEmployee);
    }

    /**
     * Retrieves a paginated list of all employees.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployees(Pageable pageable) {
        Page<EmployeeEntity> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employeeMapper::toDto);
    }

    /**
     * Retrieves a single employee by their ID.
     */
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        EmployeeEntity employee = findEmployeeById(id);
        return employeeMapper.toDto(employee);
    }

    /**
     * Updates an existing employee's profile, status, and roles.
     */
    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateDto updateDto) {
        EmployeeEntity existingEmployee = findEmployeeById(id);
        UserEntity user = existingEmployee.getUser();

        // Update roles
        List<RoleEntity> rolesList = roleRepository.findAllById(updateDto.roleIds());
        if (rolesList.size() != updateDto.roleIds().size()) {
            throw new NotFoundException("One or more specified roles could not be found.");
        }
        user.setRoles(new HashSet<>(rolesList));

        // Update user status
        user.setStatus(updateDto.status());

        // Update profile information using the mapper
        employeeMapper.updateProfileFromDto(updateDto, existingEmployee);

        // Update assigned station
        if (updateDto.assignedStationId() != null) {
            StationEntity station = stationRepository.findById(updateDto.assignedStationId())
                    .orElseThrow(() -> new NotFoundException("Assigned station with ID " + updateDto.assignedStationId() + " not found."));
            existingEmployee.setAssignedStation(station);
        } else {
            existingEmployee.setAssignedStation(null); // Allow un-assigning a station
        }

        // Save the updated employee. Changes to the user will be cascaded.
        EmployeeEntity savedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toDto(savedEmployee);
    }

    /**
     * Deletes an employee and their associated user account.
     */
    @Transactional
    public void deleteEmployee(Long id) {
        EmployeeEntity employee = findEmployeeById(id);
        // Because of orphanRemoval=true on the UserEntity link,
        // deleting the employee will also delete their user account.
        employeeRepository.delete(employee);
    }

    /**
     * Private helper method to find an employee by ID or throw a NotFoundException.
     */
    private EmployeeEntity findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with ID " + id + " not found."));
    }

}