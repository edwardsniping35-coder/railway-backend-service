package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.dto.ApiResponse;
import com.ryzera.railwayservice.dto.PagedApiResponse;
import com.ryzera.railwayservice.routes.dto.RouteCreateDto;
import com.ryzera.railwayservice.routes.dto.RouteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('route:manage')")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<ApiResponse<RouteDto.RouteResponseDto>> createRoute(@Valid @RequestBody RouteCreateDto createDto) {
        RouteDto.RouteResponseDto newRoute = routeService.createRoute(createDto);
        return new ResponseEntity<>(ApiResponse.success(newRoute, "Route created successfully."), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedApiResponse<RouteDto.RouteResponseDto>>> getAllRoutes(Pageable pageable) {
        Page<RouteDto.RouteResponseDto> routePage = routeService.getAllRoutes(pageable);
        return ResponseEntity.ok(ApiResponse.success(PagedApiResponse.fromPage(routePage), "Routes retrieved successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteDto.RouteResponseDto>> getRouteById(@PathVariable Long id) {
        RouteDto.RouteResponseDto route = routeService.getRouteById(id);
        return ResponseEntity.ok(ApiResponse.success(route, "Route retrieved successfully."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteDto.RouteResponseDto>> updateRoute(@PathVariable Long id, @Valid @RequestBody RouteDto.RouteUpdateDto updateDto) {
        RouteDto.RouteResponseDto updatedRoute = routeService.updateRoute(id, updateDto);
        return ResponseEntity.ok(ApiResponse.success(updatedRoute, "Route updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}