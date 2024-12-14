package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import edu.bbte.idde.dhim2228.mapper.UserMapper;
import edu.bbte.idde.dhim2228.model.User;
import edu.bbte.idde.dhim2228.repository.UserRepository;
import edu.bbte.idde.dhim2228.service.UserService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Long save(UserRequestDto userRequestDto) {
        log.info("Saving user: {}", userRequestDto);
        if (userRepository.findByUsername(userRequestDto.getUsername()) != null) {
            log.error("Username {} already exists", userRequestDto.getUsername());
            throw new UserAlreadyExistException("Username already exists");
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()) != null) {
            log.error("Email {} already exists", userRequestDto.getEmail());
            throw new UserAlreadyExistException("Email already exists");
        }


        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRequestDto.getPassword()));
        return userRepository.save(user).getId();
    }

    private void checkExistsUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User with id {} not found", id);
            throw new NotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public void update(Long id, UserRequestDto userRequestDto) {
        log.info("Updating user: {}", userRequestDto);
        checkExistsUserById(id);
        User user = userMapper.toEntity(userRequestDto);
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public Collection<UserResponseDto> findAllUser() {
        log.info("Finding all users");
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        log.info("Finding user by id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User with id {} not found", id);
            throw new NotFoundException("User with id " + id + " not found");
        }
        return userMapper.toResponseDto(user.get());
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user by id: {}", id);
        checkExistsUserById(id);
        userRepository.deleteById(id);
    }
}
