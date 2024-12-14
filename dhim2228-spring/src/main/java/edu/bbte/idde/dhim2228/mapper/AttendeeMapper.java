package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeResponseDto;
import edu.bbte.idde.dhim2228.model.Attendee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AttendeeMapper {
    @Mapping(target = "id", ignore = true)
    Attendee toEntityDto(Attendee attendee);

    Attendee toResponseDto(Attendee attendee);

    Collection<AttendeeResponseDto> toResponseDtoList(Collection<Attendee> attendees);
}
