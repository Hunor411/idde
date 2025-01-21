package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import edu.bbte.idde.dhim2228.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = AttendeeMapper.class)
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "attendees", ignore = true)
    Event toEntityDto(EventRequestDto dto);

    @Mapping(target = "attendees", source = "attendees")
    EventResponseDto toResponseDto(Event entity);

    EventShortResponseDto toShortResponseDto(Event event);

    default Page<EventShortResponseDto> toShortResponseDtoList(Page<Event> entities) {
        return entities.map(this::toShortResponseDto);
    }
}
