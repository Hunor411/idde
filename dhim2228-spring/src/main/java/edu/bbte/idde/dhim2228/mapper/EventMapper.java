package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    Event toEntityDto(EventRequestDto dto);

    EventResponseDto toResponseDto(Event entity);

    List<EventResponseDto> toResponseDtoList(Collection<Event> entities);
}
