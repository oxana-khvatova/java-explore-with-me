package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}
