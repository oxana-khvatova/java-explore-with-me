package ru.practicum.explorewhithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    String annotation;

    CategoryDto category;

    Long confirmedRequest;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    @NotNull
    @Size(max = 300)
    String description;

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    User initiator;

    @NotNull
    Boolean paid;

    Long participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    Boolean requestModeration;
    Status state;
    @NotNull
    String title;
    Long views;
}
