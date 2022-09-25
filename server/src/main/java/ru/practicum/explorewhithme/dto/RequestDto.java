package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explorewhithme.model.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private Long eventId;
    private Long requesterId;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
