package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explorewhithme.AppConstants;
import ru.practicum.explorewhithme.model.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {
    @JsonAlias("eventId")
    private Long id;
    private String annotation;

    @JsonProperty("category")
    private Long categoryId;

    @JsonProperty("users")
    private Long initiatorId;

    private Long confirmedRequest;

    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime createdOn;

    @NotNull
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @Future
    @JsonFormat(pattern = AppConstants.DATE_FORMATTER)
    private LocalDateTime eventDate;

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
