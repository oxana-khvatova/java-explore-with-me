package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explorewhithme.AppConstants;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = AppConstants.dateFormat)
    private LocalDateTime timestamp;
}
