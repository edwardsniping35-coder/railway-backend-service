package com.ryzera.railwayservice.auth;

import com.ryzera.railwayservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTOs for the login request and response
record LoginRequest(String mobileNumber, String password) {}
record LoginResponse(String token) {}

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.mobileNumber(), request.password())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.mobileNumber());
        final String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}