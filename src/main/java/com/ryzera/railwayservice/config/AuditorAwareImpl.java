package com.ryzera.railwayservice.config;

import com.ryzera.railwayservice.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of AuditorAware interface.
 * This class is used by Spring Data JPA to automatically set the @CreatedBy and @LastModifiedBy fields.
 */
public class AuditorAwareImpl implements AuditorAware<Long> {

    /**
     * Retrieves the ID of the currently authenticated user from the SecurityContext.
     * @return An Optional containing the user's ID, or an empty Optional if no user is authenticated.
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        // Get the current authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if there is a user and if they are authenticated
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {

            // No authenticated user, so we return an empty Optional
            return Optional.empty();
        }

        // Get the principal object, which we know is our CustomUserDetails instance
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        // Return the ID of the logged-in user
        return Optional.of(userPrincipal.getId());
    }

}