package com.ryzera.railwayservice.stations;

import com.ryzera.railwayservice.dto.ApiResponse;
import com.ryzera.railwayservice.dto.PagedApiResponse;
import com.ryzera.railwayservice.stations.dto.StationCreateDto;
import com.ryzera.railwayservice.stations.dto.StationResponseDto;
import com.ryzera.railwayservice.stations.dto.StationUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<ApiResponse<StationResponseDto>> createStation(@Valid @RequestBody StationCreateDto createDto) {
        StationResponseDto newStation = stationService.createStation(createDto);
        ApiResponse<StationResponseDto> response = ApiResponse.success(newStation, "Station created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedApiResponse<StationResponseDto>>> getAllStations(Pageable pageable) {
        Page<StationResponseDto> stationPage = stationService.getAllStations(pageable);
        PagedApiResponse<StationResponseDto> pagedResponse = PagedApiResponse.fromPage(stationPage);
        ApiResponse<PagedApiResponse<StationResponseDto>> response = ApiResponse.success(pagedResponse, "Stations retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StationResponseDto>> getStationById(@PathVariable Long id) {
        StationResponseDto station = stationService.getStationById(id);
        ApiResponse<StationResponseDto> response = ApiResponse.success(station, "Station retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StationResponseDto>> updateStation(@PathVariable Long id, @Valid @RequestBody StationUpdateDto updateDto) {
        StationResponseDto updatedStation = stationService.updateStation(id, updateDto);
        ApiResponse<StationResponseDto> response = ApiResponse.success(updatedStation, "Station updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

}
