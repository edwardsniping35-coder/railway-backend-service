package com.ryzera.railwayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import com.ryzera.railwayservice.dto.PageMetadata;

import java.util.List;

/**
 * A wrapper for paginated API responses.
 * @param <T> The type of the content in the list.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedApiResponse<T> {

    private List<T> content;
    private PageMetadata metadata;

    /**
     * A convenient factory method to create a PagedApiResponse directly from a Spring Data Page object.
     * @param page The Page object returned from the repository.
     * @return A new PagedApiResponse instance.
     * @param <T> The type of the content in the page.
     */
    public static <T> PagedApiResponse<T> fromPage(Page<T> page) {
        List<T> content = page.getContent();
        PageMetadata metadata = new PageMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
        return new PagedApiResponse<>(content, metadata);
    }
}