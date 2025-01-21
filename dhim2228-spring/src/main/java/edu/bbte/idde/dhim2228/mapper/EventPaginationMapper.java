package edu.bbte.idde.dhim2228.mapper;

import edu.bbte.idde.dhim2228.dto.PaginatedResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface EventPaginationMapper {
    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "number")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "last", source = "last")
    @Mapping(target = "first", source = "first")
    @Mapping(target = "previousPage", source = ".", qualifiedByName = "getPreviousPage")
    @Mapping(target = "nextPage", source = ".", qualifiedByName = "getNextPage")
    PaginatedResponseDto<EventShortResponseDto> toPaginatedResponse(Page<EventShortResponseDto> page);

    @Named("getPreviousPage")
    default Integer getPreviousPage(Page<EventShortResponseDto> page) {
        return page.hasPrevious() ? page.getNumber() - 1 : null;
    }

    @Named("getNextPage")
    default Integer getNextPage(Page<EventShortResponseDto> page) {
        return page.hasNext() ? page.getNumber() + 1 : null;
    }
}
