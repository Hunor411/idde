package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService {
    Long save(UserRequestDto userRequestDto);

    void update(Long id, UserRequestDto userRequestDto);

    Collection<UserResponseDto> findAllUser();

    UserResponseDto findUserById(Long id);

    void deleteUser(Long id);
}
