package edu.bbte.idde.dhim2228.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedResponseDto<T> {
    private List<T> content = new ArrayList<>();
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private Integer previousPage;
    private Integer nextPage;
}
