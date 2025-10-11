package com.ryzera.railwayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    // --- Static Factory Methods for Convenience ---

    /**
     * Creates a standard success response with data and a message.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates a simple success response with data and a default message.
     */
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Request was successful");
    }

    /**
     * Creates a standard error response with a message.
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }

}