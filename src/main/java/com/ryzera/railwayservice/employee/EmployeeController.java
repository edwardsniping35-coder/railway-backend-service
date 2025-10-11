package com.ryzera.railwayservice.employee;

import com.ryzera.railwayservice.dto.ApiResponse;
import com.ryzera.railwayservice.dto.PagedApiResponse;
import com.ryzera.railwayservice.employee.dto.EmployeeCreateDto;
import com.ryzera.railwayservice.employee.dto.EmployeeResponseDto;
import com.ryzera.railwayservice.employee.dto.EmployeeUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasAuthority('user:manage')") // Secure the entire controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> createEmployee(@Valid @RequestBody EmployeeCreateDto createDto) {
        EmployeeResponseDto newEmployee = employeeService.createEmployee(createDto);
        ApiResponse<EmployeeResponseDto> response = ApiResponse.success(newEmployee, "Employee created successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedApiResponse<EmployeeResponseDto>>> getAllEmployees(Pageable pageable) {
        Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployees(pageable);
        PagedApiResponse<EmployeeResponseDto> pagedResponse = PagedApiResponse.fromPage(employeePage);
        ApiResponse<PagedApiResponse<EmployeeResponseDto>> response = ApiResponse.success(pagedResponse, "Employees retrieved successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(id);
        ApiResponse<EmployeeResponseDto> response = ApiResponse.success(employee, "Employee retrieved successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDto updateDto) {
        EmployeeResponseDto updatedEmployee = employeeService.updateEmployee(id, updateDto);
        ApiResponse<EmployeeResponseDto> response = ApiResponse.success(updatedEmployee, "Employee updated successfully.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}