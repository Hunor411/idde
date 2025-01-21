package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import edu.bbte.idde.dhim2228.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = AttendeeMapper.class)
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "attendees", ignore = true)
    Event toEntityDto(EventRequestDto dto);

    @Mapping(target = "attendees", source = "attendees")
    EventResponseDto toResponseDto(Event entity);

    EventShortResponseDto toShortResponseDto(Event event);

    Collection<EventShortResponseDto> toShortResponseDtoList(Collection<Event> entities);
}
