package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewhithme.AppConstants;
import ru.practicum.explorewhithme.model.Status;
import ru.practicum.explorewhithme.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;

    private CategoryDto category;

    private Long confirmedRequest;

    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime createdOn;

    @NotNull
    @Size(max = 300)
    private String description;

    @NotNull
    @Future
    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime eventDate;

    private User initiator;

    @NotNull
    private Boolean paid;

    private Long participantLimit;

    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;
    private Status state;
    @NotNull
    private String title;
    private Long views;
}
