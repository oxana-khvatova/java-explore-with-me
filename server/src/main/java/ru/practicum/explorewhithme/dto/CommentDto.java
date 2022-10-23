package ru.practicum.explorewhithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private Long eventId;
    private Long authorId;
    private LocalDateTime created;
}
