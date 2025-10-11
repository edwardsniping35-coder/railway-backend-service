package com.ryzera.railwayservice.stations;

import com.ryzera.railwayservice.exception.ConflictException;
import com.ryzera.railwayservice.exception.NotFoundException;
import com.ryzera.railwayservice.stations.dto.StationCreateDto;
import com.ryzera.railwayservice.stations.dto.StationResponseDto;
import com.ryzera.railwayservice.stations.dto.StationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Transactional
    public StationResponseDto createStation(StationCreateDto createDto) {
        stationRepository.findByCode(createDto.code()).ifPresent(entity -> {
            throw new ConflictException("Station with code '" + createDto.code() + "' already exists.");
        });

        StationEntity stationEntity = stationMapper.toEntity(createDto);
        StationEntity savedEntity = stationRepository.save(stationEntity);
        return stationMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public Page<StationResponseDto> getAllStations(Pageable pageable) {
        return stationRepository.findAll(pageable).map(stationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public StationResponseDto getStationById(Long id) {
        return stationMapper.toDto(findStationById(id));
    }

    @Transactional
    public StationResponseDto updateStation(Long id, StationUpdateDto updateDto) {
        StationEntity existingStation = findStationById(id);

        Optional<StationEntity> conflictingStation = stationRepository.findByCode(updateDto.code());
        if (conflictingStation.isPresent() && !conflictingStation.get().getId().equals(id)) {
            throw new ConflictException("Station with code '" + updateDto.code() + "' already exists.");
        }

        stationMapper.updateEntityFromDto(updateDto, existingStation);
        StationEntity updatedEntity = stationRepository.save(existingStation);
        return stationMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new NotFoundException("Station with ID " + id + " not found.");
        }
        stationRepository.deleteById(id);
    }

    private StationEntity findStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Station with ID " + id + " not found."));
    }
}
