package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserShortResponseDto;
import edu.bbte.idde.dhim2228.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = AttendeeMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    UserShortResponseDto toShortResponseDto(User user);

    @Mapping(target = "events", source = "events")
    UserResponseDto toResponseDto(User user);

    default Page<UserShortResponseDto> toShortDtoList(Page<User> users) {
        return users.map(this::toShortResponseDto);
    }
}