package com.ryzera.railwayservice.parcel;

import com.ryzera.railwayservice.dto.ApiResponse;
import com.ryzera.railwayservice.dto.PagedApiResponse;
import com.ryzera.railwayservice.parcel.dto.ParcelDto;
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
@RequestMapping("/api/v1/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    /**
     * Endpoint for a CUSTOMER to create a new parcel.
     * Requires the 'parcel:create' authority (which should be given to customer roles).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('parcel:create')")
    public ResponseEntity<ApiResponse<ParcelDto.ParcelResponseDto>> createParcel(@Valid @RequestBody ParcelDto.ParcelCreateDto createDto) {
        ParcelDto.ParcelResponseDto newParcel = parcelService.createParcel(createDto);
        ApiResponse<ParcelDto.ParcelResponseDto> response = ApiResponse.success(newParcel, "Parcel submitted for verification successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint for an EMPLOYEE to verify/approve/reject a parcel.
     * Requires the 'parcel:manage' authority (which should be given to employee roles).
     */
    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('parcel:manage')")
    public ResponseEntity<ApiResponse<ParcelDto.ParcelResponseDto>> verifyParcel(
            @PathVariable Long id,
            @Valid @RequestBody ParcelDto.ParcelVerificationDto verificationDto
    ) {
        ParcelDto.ParcelResponseDto verifiedParcel = parcelService.verifyParcel(id, verificationDto);
        ApiResponse<ParcelDto.ParcelResponseDto> response = ApiResponse.success(verifiedParcel, "Parcel status updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('parcel:read')")
    public ResponseEntity<ApiResponse<PagedApiResponse<ParcelDto.ParcelResponseDto>>> getAllParcels(Pageable pageable) {
        Page<ParcelDto.ParcelResponseDto> parcelPage = parcelService.getAllParcels(pageable);
        PagedApiResponse<ParcelDto.ParcelResponseDto> pagedResponse = PagedApiResponse.fromPage(parcelPage);
        ApiResponse<PagedApiResponse<ParcelDto.ParcelResponseDto>> response = ApiResponse.success(pagedResponse, "Parcels retrieved successfully");
        return ResponseEntity.ok(response);
    }

}

