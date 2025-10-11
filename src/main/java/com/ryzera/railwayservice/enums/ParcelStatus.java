package com.ryzera.railwayservice.enums;

public enum ParcelStatus {
    PENDING_VERIFICATION, // New initial state set by the customer
    PENDING,              // Approved by an employee, waiting for dispatch
    IN_TRANSIT,
    DELIVERED,
    CANCELLED,
    REJECTED              // If an employee rejects the parcel
}
