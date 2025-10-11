package com.ryzera.railwayservice.parcel;

import com.ryzera.railwayservice.enums.ParcelStatus;
import com.ryzera.railwayservice.exception.NotFoundException;
import com.ryzera.railwayservice.parcel.dto.ParcelDto;
import com.ryzera.railwayservice.parcel.entity.ParcelEntity;
import com.ryzera.railwayservice.parcel.entity.ParcelItemEntity;
import com.ryzera.railwayservice.stations.StationEntity;
import com.ryzera.railwayservice.stations.StationRepository;
import com.ryzera.railwayservice.user.UserEntity;
import com.ryzera.railwayservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final ParcelMapper parcelMapper;

    /**
     * Creates a new parcel. This action is performed by an authenticated CUSTOMER.
     */
    @Transactional
    public ParcelDto.ParcelResponseDto createParcel(ParcelDto.ParcelCreateDto createDto) {
        // 1. Get the SENDER (the logged-in customer) from the security token
        String senderMobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity senderUser = userRepository.findByMobileNumber(senderMobileNumber)
                .orElseThrow(() -> new NotFoundException("Authenticated sender with mobile number " + senderMobileNumber + " not found."));

        // 2. Fetch related entities
        StationEntity origin = stationRepository.findById(createDto.originStationId())
                .orElseThrow(() -> new NotFoundException("Origin station not found."));
        StationEntity destination = stationRepository.findById(createDto.destinationStationId())
                .orElseThrow(() -> new NotFoundException("Destination station not found."));

        // 3. Create the ParcelEntity
        ParcelEntity newParcel = new ParcelEntity();
        newParcel.setTrackingId("SLR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        newParcel.setStatus(ParcelStatus.PENDING_VERIFICATION); // Set the initial status
        newParcel.setOriginStation(origin);
        newParcel.setDestinationStation(destination);
        newParcel.setSenderUser(senderUser); // The customer from the token
        // processedByUser is intentionally left NULL on creation
        newParcel.setReceiverName(createDto.receiverName());
        newParcel.setReceiverPhone(createDto.receiverPhone());
        newParcel.setDescription(createDto.description());

        // 4. Create and link ParcelItemEntities
        List<ParcelItemEntity> items = createDto.items().stream().map(itemDto -> {
            ParcelItemEntity item = new ParcelItemEntity();
            item.setParcel(newParcel);
            item.setType(itemDto.type());
            item.setWeightInKg(itemDto.weightInKg());
            item.setQuantity(itemDto.quantity());
            item.setFee(itemDto.fee());
            return item;
        }).collect(Collectors.toList());
        newParcel.setItems(items);

        // 5. Calculate totals
        newParcel.setTotalWeightInKg(items.stream().mapToDouble(i -> i.getWeightInKg() * i.getQuantity()).sum());
        newParcel.setTotalPrice(items.stream().map(i -> i.getFee().multiply(BigDecimal.valueOf(i.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add));

        return parcelMapper.toDto(parcelRepository.save(newParcel));
    }

    /**
     * Verifies a parcel. This action is performed by an authenticated EMPLOYEE.
     */
    @Transactional
    public ParcelDto.ParcelResponseDto verifyParcel(Long parcelId, ParcelDto.ParcelVerificationDto verificationDto) {
        // 1. Get the PROCESSOR (the logged-in employee) from the security token
        String processorMobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity processedByUser = userRepository.findByMobileNumber(processorMobileNumber)
                .orElseThrow(() -> new NotFoundException("Authenticated employee not found."));

        // 2. Find the parcel to be verified
        ParcelEntity parcelToVerify = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new NotFoundException("Parcel with ID " + parcelId + " not found."));

        // 3. Update the parcel's status and set the processor
        parcelToVerify.setStatus(verificationDto.newStatus());
        parcelToVerify.setProcessedByUser(processedByUser);

        return parcelMapper.toDto(parcelRepository.save(parcelToVerify));
    }

    @Transactional(readOnly = true)
    public Page<ParcelDto.ParcelResponseDto> getAllParcels(Pageable pageable) {
        return parcelRepository.findAll(pageable).map(parcelMapper::toDto);
    }
}

