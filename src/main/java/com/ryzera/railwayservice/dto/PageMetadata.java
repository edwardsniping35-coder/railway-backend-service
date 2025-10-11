package com.ryzera.railwayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains the metadata for a paginated response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata {
    private int pageNumber;      // The current page number (0-indexed)
    private int pageSize;        // The number of items per page
    private long totalElements;  // The total number of items across all pages
    private int totalPages;      // The total number of pages
    private boolean last;        // Is this the last page?
    private boolean first;       // Is this the first page?
}