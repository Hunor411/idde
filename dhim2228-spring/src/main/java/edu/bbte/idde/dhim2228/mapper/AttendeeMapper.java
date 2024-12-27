package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserEventDetailsResponseDto;
import edu.bbte.idde.dhim2228.model.Attendee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendeeMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "userStatus")
    UserEventDetailsResponseDto toUserEventDetailsResponseDto(Attendee attendee);

    List<UserEventDetailsResponseDto> toUserEventDetailsResponseDtoList(List<Attendee> attendees);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "userStatus")
    EventUserDetailsResponseDto toEventUserDetailsResponseDto(Attendee attendee);

    List<EventUserDetailsResponseDto> toEventUserDetailsResponseDtoList(List<Attendee> attendees);
}