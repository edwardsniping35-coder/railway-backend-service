package com.ryzera.railwayservice.train;

import com.ryzera.railwayservice.exception.ConflictException;
import com.ryzera.railwayservice.exception.NotFoundException;
import com.ryzera.railwayservice.train.dto.TrainCreateDto;
import com.ryzera.railwayservice.train.dto.TrainResponseDto;
import com.ryzera.railwayservice.train.dto.TrainUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;

    /**
     * Creates a new train after validating that the trainId is not already in use.
     */
    @Transactional
    public TrainResponseDto createTrain(TrainCreateDto createDto) {
        // Check for conflict: ensure trainId is unique
        trainRepository.findByTrainId(createDto.trainId()).ifPresent(entity -> {
            throw new ConflictException("Train with trainId '" + createDto.trainId() + "' already exists.");
        });

        TrainEntity trainEntity = trainMapper.toEntity(createDto);
        TrainEntity savedEntity = trainRepository.save(trainEntity);
        return trainMapper.toDto(savedEntity);
    }

    /**
     * Retrieves a paginated list of all trains.
     */
    @Transactional(readOnly = true)
    public Page<TrainResponseDto> getAllTrains(Pageable pageable) {
        Page<TrainEntity> trainEntityPage = trainRepository.findAll(pageable);
        return trainEntityPage.map(trainMapper::toDto);
    }

    /**
     * Retrieves a single train by its ID.
     */
    @Transactional(readOnly = true)
    public TrainResponseDto getTrainById(Long id) {
        TrainEntity trainEntity = findTrainById(id);
        return trainMapper.toDto(trainEntity);
    }

    /**
     * Updates an existing train.
     */
    @Transactional
    public TrainResponseDto updateTrain(Long id, TrainUpdateDto updateDto) {
        TrainEntity existingTrain = findTrainById(id);

        // Check for conflict: if trainId is being changed, ensure it's not taken by another train
        Optional<TrainEntity> conflictingTrain = trainRepository.findByTrainId(updateDto.trainId());
        if (conflictingTrain.isPresent() && !conflictingTrain.get().getId().equals(id)) {
            throw new ConflictException("Train with trainId '" + updateDto.trainId() + "' already exists.");
        }

        // Use MapStruct to update the entity's fields from the DTO
        trainMapper.updateEntityFromDto(updateDto, existingTrain);

        TrainEntity updatedEntity = trainRepository.save(existingTrain);
        return trainMapper.toDto(updatedEntity);
    }

    /**
     * Deletes a train by its ID.
     */
    @Transactional
    public void deleteTrain(Long id) {
        // Ensure the train exists before attempting to delete
        if (!trainRepository.existsById(id)) {
            throw new NotFoundException("Train with ID " + id + " not found.");
        }
        trainRepository.deleteById(id);
    }

    /**
     * Private helper method to find a train by ID or throw a NotFoundException.
     * This avoids code duplication.
     */
    private TrainEntity findTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Train with ID " + id + " not found."));
    }
}