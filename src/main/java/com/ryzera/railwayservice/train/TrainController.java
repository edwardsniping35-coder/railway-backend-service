package com.ryzera.railwayservice.train;

import com.ryzera.railwayservice.dto.ApiResponse;
import com.ryzera.railwayservice.dto.PagedApiResponse;
import com.ryzera.railwayservice.train.dto.TrainCreateDto;
import com.ryzera.railwayservice.train.dto.TrainResponseDto;
import com.ryzera.railwayservice.train.dto.TrainUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trains")
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    @PreAuthorize("hasAuthority('train:manage')")
    public ResponseEntity<ApiResponse<TrainResponseDto>> createTrain(@Valid @RequestBody TrainCreateDto createDto) {
        TrainResponseDto newTrain = trainService.createTrain(createDto);
        ApiResponse<TrainResponseDto> response = ApiResponse.success(newTrain, "Train created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('train:manage')")
    public ResponseEntity<ApiResponse<PagedApiResponse<TrainResponseDto>>> getAllTrains(Pageable pageable) {
        Page<TrainResponseDto> trainPage = trainService.getAllTrains(pageable);
        PagedApiResponse<TrainResponseDto> pagedResponse = PagedApiResponse.fromPage(trainPage);
        ApiResponse<PagedApiResponse<TrainResponseDto>> response = ApiResponse.success(pagedResponse, "Trains retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('train:manage')")
    public ResponseEntity<ApiResponse<TrainResponseDto>> getTrainById(@PathVariable Long id) {
        TrainResponseDto train = trainService.getTrainById(id);
        ApiResponse<TrainResponseDto> response = ApiResponse.success(train, "Train retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('train:manage')")
    public ResponseEntity<ApiResponse<TrainResponseDto>> updateTrain(@PathVariable Long id, @Valid @RequestBody TrainUpdateDto updateDto) {
        TrainResponseDto updatedTrain = trainService.updateTrain(id, updateDto);
        ApiResponse<TrainResponseDto> response = ApiResponse.success(updatedTrain, "Train updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('train:manage')")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.noContent().build(); // Standard practice for DELETE
    }

}
