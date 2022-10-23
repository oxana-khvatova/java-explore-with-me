package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {
    private List<EventDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
