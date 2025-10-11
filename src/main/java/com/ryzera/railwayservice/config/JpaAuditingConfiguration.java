package com.ryzera.railwayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware; // <-- IMPORT
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Central configuration class for JPA Auditing.
 * Enables auditing and provides the necessary beans for setting creator/modifier
 * and creation/modification timestamps.
 */
@Configuration
// This single annotation now controls all auditing settings
@EnableJpaAuditing(
        auditorAwareRef = "auditorAware",
        dateTimeProviderRef = "dateTimeProvider"
)
public class JpaAuditingConfiguration {

    /**
     * Provides the ID of the current authenticated user for @CreatedBy and @LastModifiedBy.
     * @return AuditorAware bean implementation.
     */
    @Bean
    public AuditorAware<Long> auditorAware() {
        // We assume AuditorAwareImpl is in the config package or a security package
        return new AuditorAwareImpl();
    }

    /**
     * Provides the current date and time for @CreatedDate and @LastModifiedDate.
     * Overrides the default provider to return an OffsetDateTime.
     * @return DateTimeProvider bean implementation.
     */
    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}