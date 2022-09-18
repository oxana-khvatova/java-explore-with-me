package dto;

import model.Category;
import model.Status;
import model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class EventDto {
    private Long id;
    String annotation;
    Category category;
    Long confirmedRequest;
    LocalDateTime createdOn;
    @NotNull
    @Size(max = 300)
    String description;
    LocalDateTime eventDate;
    User initiator;
    @NotNull
    Boolean paid;
    Long participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    Status state;
    @NotNull
    String title;
    Long views;
}
