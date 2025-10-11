package com.ryzera.railwayservice.parcel.dto;

import com.ryzera.railwayservice.enums.ParcelStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class ParcelDto {

    public record ParcelItemCreateDto(
            @NotBlank String type,
            String category,
            @NotNull @Positive Double weightInKg,
            @NotNull @Positive Integer quantity,
            @NotNull @Positive BigDecimal fee
    ) {}

    /** DTO for when a CUSTOMER creates a new parcel */
    public record ParcelCreateDto(
            @NotNull Long originStationId,
            @NotNull Long destinationStationId,
            // The senderMobileNumber is REMOVED. It will be taken from the token.
            @NotBlank String receiverName,
            @NotBlank String receiverPhone,
            String description,
            @Valid @NotEmpty List<ParcelItemCreateDto> items
    ) {}

    /** DTO for when an EMPLOYEE verifies a parcel */
    public record ParcelVerificationDto(
            @NotNull ParcelStatus newStatus // e.g., PENDING, REJECTED
            // You could add other fields here, like a rejection reason
    ) {}

    // Response DTOs remain largely the same
    public record ParcelItemResponseDto(Long id, String type, Double weightInKg, Integer quantity, BigDecimal fee) {}
    public record StationDto(Long id, String name, String code) {}
    public record UserDto(Long id, String mobileNumber) {}
    public record TrainDto(Long id, String name, String trainId) {}

    public record ParcelResponseDto(
            Long id,
            String trackingId,
            ParcelStatus status,
            StationDto originStation,
            StationDto destinationStation,
            UserDto senderUser,
            UserDto processedByUser, // This will be null until verified
            String receiverName,
            String receiverPhone,
            String description,
            Double totalWeightInKg,
            BigDecimal totalPrice,
            List<ParcelItemResponseDto> items,
            OffsetDateTime createdAt
    ) {}
}

