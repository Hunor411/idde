package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.user.UserLoginRequestDto;
import edu.bbte.idde.dhim2228.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        ResponseCookie jwtCookie = authenticationService.login(userLoginRequestDto);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> logout() {
        ResponseCookie jwtCookie = authenticationService.logout();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
    }
}
