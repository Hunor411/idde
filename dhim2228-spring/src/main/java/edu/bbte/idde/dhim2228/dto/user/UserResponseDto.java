package edu.bbte.idde.dhim2228.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private List<UserEventDetailsResponseDto> events;
}
