package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStatsDto {
    String app;
    String uri;
    Long hits;
}
