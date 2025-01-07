package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserShortResponseDto;
import edu.bbte.idde.dhim2228.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService extends UserDetailsService {
    Long save(UserRequestDto userRequestDto);

    void update(Long id, UserRequestDto userRequestDto);

    Collection<UserShortResponseDto> findAllUser();

    UserResponseDto findUserById(Long id);

    void deleteUser(Long id);

    User loadUserByUsername(String username);
}
