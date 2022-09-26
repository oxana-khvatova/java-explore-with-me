package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EndpointHitDto {
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
