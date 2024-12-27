package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserShortResponseDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService {
    Long save(UserRequestDto userRequestDto);

    void update(Long id, UserRequestDto userRequestDto);

    Collection<UserShortResponseDto> findAllUser();

    UserResponseDto findUserById(Long id);

    void deleteUser(Long id);
}
