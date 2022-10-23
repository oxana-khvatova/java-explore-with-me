package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explorewhithme.AppConstants;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class HitDto {
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime timestamp;
}
