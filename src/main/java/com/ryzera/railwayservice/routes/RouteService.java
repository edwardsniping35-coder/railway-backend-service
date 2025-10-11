package com.ryzera.railwayservice.routes;

import com.ryzera.railwayservice.exception.ConflictException;
import com.ryzera.railwayservice.exception.NotFoundException;
import com.ryzera.railwayservice.routes.dto.RouteCreateDto;
import com.ryzera.railwayservice.routes.dto.RouteDto;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.stations.StationRepository;
import com.ryzera.railwayservice.train.TrainEntity;
import com.ryzera.railwayservice.train.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final RouteMapper routeMapper;

    @Transactional
    public RouteDto.RouteResponseDto createRoute(RouteCreateDto createDto) {
        routeRepository.findByRouteNumber(createDto.routeNumber()).ifPresent(r -> {
            throw new ConflictException("Route with number " + createDto.routeNumber() + " already exists.");
        });

        // 1. Fetch related entities
        TrainEntity train = trainRepository.findById(createDto.trainId())
                .orElseThrow(() -> new NotFoundException("Train with ID " + createDto.trainId() + " not found."));
        StationEntity origin = stationRepository.findById(createDto.originStationId())
                .orElseThrow(() -> new NotFoundException("Origin station with ID " + createDto.originStationId() + " not found."));
        StationEntity destination = stationRepository.findById(createDto.destinationStationId())
                .orElseThrow(() -> new NotFoundException("Destination station with ID " + createDto.destinationStationId() + " not found."));

        // 2. Create the main RouteEntity
        RouteEntity newRoute = new RouteEntity();
        newRoute.setRouteName(createDto.routeName());
        newRoute.setRouteNumber(createDto.routeNumber());
        newRoute.setStatus(createDto.status());
        newRoute.setTrain(train);
        newRoute.setOriginStation(origin);
        newRoute.setDestinationStation(destination);

        // 3. Create and link the RouteStopEntities
        List<RouteStopEntity> stops = createDto.stops().stream().map(stopDto -> {
            StationEntity stopStation = stationRepository.findById(stopDto.stationId())
                    .orElseThrow(() -> new NotFoundException("Station with ID " + stopDto.stationId() + " for a stop not found."));

            RouteStopEntity stopEntity = new RouteStopEntity();
            stopEntity.setStation(stopStation);
            stopEntity.setStopOrder(stopDto.stopOrder());
            stopEntity.setScheduledArrivalTime(stopDto.scheduledArrivalTime());
            stopEntity.setScheduledDepartureTime(stopDto.scheduledDepartureTime());
            stopEntity.setDistanceFromOriginKm(stopDto.distanceFromOriginKm());
            stopEntity.setRoute(newRoute); // Set the back-reference to the parent route
            return stopEntity;
        }).collect(Collectors.toList());

        newRoute.getStops().addAll(stops);

        // 4. Save the RouteEntity. Stops will be saved automatically due to cascading.
        RouteEntity savedRoute = routeRepository.save(newRoute);
        return routeMapper.toDto(savedRoute);
    }

    @Transactional(readOnly = true)
    public Page<RouteDto.RouteResponseDto> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable).map(routeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public RouteDto.RouteResponseDto getRouteById(Long id) {
        return routeMapper.toDto(findRouteById(id));
    }

    @Transactional
    public RouteDto.RouteResponseDto updateRoute(Long id, RouteDto.RouteUpdateDto updateDto) {
        RouteEntity existingRoute = findRouteById(id);

        // Fetch related entities
        TrainEntity train = trainRepository.findById(updateDto.trainId())
                .orElseThrow(() -> new NotFoundException("Train with ID " + updateDto.trainId() + " not found."));
        StationEntity origin = stationRepository.findById(updateDto.originStationId())
                .orElseThrow(() -> new NotFoundException("Origin station with ID " + updateDto.originStationId() + " not found."));
        StationEntity destination = stationRepository.findById(updateDto.destinationStationId())
                .orElseThrow(() -> new NotFoundException("Destination station with ID " + updateDto.destinationStationId() + " not found."));

        // Update main properties
        existingRoute.setRouteName(updateDto.routeName());
        existingRoute.setStatus(updateDto.status());
        existingRoute.setTrain(train);
        existingRoute.setOriginStation(origin);
        existingRoute.setDestinationStation(destination);

        // The cleanest way to handle nested collections is to clear the old ones and add the new ones.
        // `orphanRemoval=true` on the entity will automatically delete the old stops from the database.
        existingRoute.getStops().clear();

        List<RouteStopEntity> newStops = updateDto.stops().stream().map(stopDto -> {
            StationEntity stopStation = stationRepository.findById(stopDto.stationId())
                    .orElseThrow(() -> new NotFoundException("Station with ID " + stopDto.stationId() + " for a stop not found."));

            RouteStopEntity stopEntity = new RouteStopEntity();
            stopEntity.setStation(stopStation);
            stopEntity.setStopOrder(stopDto.stopOrder());
            stopEntity.setScheduledArrivalTime(stopDto.scheduledArrivalTime());
            stopEntity.setScheduledDepartureTime(stopDto.scheduledDepartureTime());
            stopEntity.setDistanceFromOriginKm(stopDto.distanceFromOriginKm());
            stopEntity.setRoute(existingRoute); // Link back to the parent
            return stopEntity;
        }).collect(Collectors.toList());

        existingRoute.getStops().addAll(newStops);

        RouteEntity savedRoute = routeRepository.save(existingRoute);
        return routeMapper.toDto(savedRoute);
    }

    @Transactional
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new NotFoundException("Route with ID " + id + " not found.");
        }
        routeRepository.deleteById(id);
    }

    private RouteEntity findRouteById(Long id) {
        // Use a custom query to fetch the route and its stops together to avoid lazy loading issues
        return routeRepository.findByIdWithStops(id)
                .orElseThrow(() -> new NotFoundException("Route with ID " + id + " not found."));
    }

    // ... Implement GET (all and by ID), UPDATE, and DELETE methods here following previous patterns ...
}