package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.EventShortResponseDto;
import edu.bbte.idde.dhim2228.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    Event toEntityDto(EventRequestDto dto);

    EventShortResponseDto toShortResponseDto(Event event);

    EventResponseDto toResponseDto(Event entity);

    Collection<EventShortResponseDto> toShortResponseDtoList(Collection<Event> entities);

    Collection<EventResponseDto> toResponseDtoList(Collection<Event> entities);
}
