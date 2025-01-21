package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.PaginatedResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserShortResponseDto;
import edu.bbte.idde.dhim2228.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserRequestDto user) {
        Long id = userService.save(user);
        URI createUri = URI.create("/api/users/" + id);
        return ResponseEntity.created(createUri).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponseDto<UserShortResponseDto>> getAllUsers(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUser(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser() {
        return ResponseEntity.ok(userService.getAuthenticatedUser());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto user) {
        userService.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
