package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class CompilationDto {
    List<EventDto> events;
    Long id;
    Boolean pinned;
    String title;
}
