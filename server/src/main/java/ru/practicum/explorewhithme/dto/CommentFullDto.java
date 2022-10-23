package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentFullDto {
    private Long id;
    private String text;
    private EventDto event;
    private UserDto user;
    private LocalDateTime created;
}
