package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsShortResponseDto;
import edu.bbte.idde.dhim2228.dto.user.UserEventDetailsResponseDto;
import edu.bbte.idde.dhim2228.model.Attendee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendeeMapper {

    @Mapping(target = "eventId", source = "id.eventId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "userStatus")
    UserEventDetailsResponseDto toUserEventDetailsResponseDto(Attendee attendee);

    List<UserEventDetailsResponseDto> toUserEventDetailsResponseDtoList(Collection<Attendee> attendees);

    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "userStatus")
    EventUserDetailsShortResponseDto toEventUserDetailsShortResponseDto(Attendee attendee);

    List<EventUserDetailsShortResponseDto> toEventUserDetailsShortResponseDtoList(Collection<Attendee> attendees);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "userStatus")
    EventUserDetailsResponseDto toEventUserDetailsResponseDto(Attendee attendee);

    List<EventUserDetailsResponseDto> toEventUserDetailsResponseDtoList(Collection<Attendee> attendees);
}
