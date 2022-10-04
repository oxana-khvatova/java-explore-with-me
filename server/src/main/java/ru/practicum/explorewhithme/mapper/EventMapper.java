package ru.practicum.explorewhithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.EventFullDto;
import ru.practicum.explorewhithme.dto.EventDto;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.model.Status;
import ru.practicum.explorewhithme.service.CategoryService;
import ru.practicum.explorewhithme.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventMapper {
    private final CategoryService categoryService;
    private final UserService userService;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(event.getId(),
                event.getAnnotation() != null ? event.getAnnotation() : null,
                event.getCategoryId() != null ? categoryMapper.toCategoryDto(categoryService.findById(event.getCategoryId())) : null,
                event.getConfirmedRequest() != null ? event.getConfirmedRequest() : null,
                event.getCreatedOn() != null ? event.getCreatedOn() : LocalDateTime.now(),
                event.getDescription(),
                event.getEventDate(),
                event.getInitiatorId() != null ? userService.findById(event.getInitiatorId()) : null,
                event.getPaid(),
                event.getParticipantLimit() != null ? event.getParticipantLimit() : null,
                event.getPublishedOn() != null ? event.getPublishedOn() : null,
                event.getRequestModeration() != null ? event.getRequestModeration() : null,
                event.getState() != null ? event.getState() : Status.PENDING,
                event.getTitle(),
                event.getViews() != null ? event.getViews() : null);
    }

    public EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setPaid(event.getPaid());
        eventDto.setTitle(event.getTitle());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategoryId(event.getCategoryId());
        eventDto.setInitiatorId(event.getCategoryId());
        eventDto.setConfirmedRequest(event.getConfirmedRequest());
        eventDto.setCreatedOn(event.getCreatedOn() != null ? event.getCreatedOn() : LocalDateTime.now());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        eventDto.setPublishedOn(event.getPublishedOn());
        eventDto.setRequestModeration(event.getRequestModeration());
        eventDto.setState(event.getState() != null ? event.getState() : Status.PENDING);
        eventDto.setViews(event.getViews());
        return eventDto;
    }

    public Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategoryId() != null) {
            event.setCategoryId(eventDto.getCategoryId());
        }
        if (eventDto.getConfirmedRequest() != null) {
            event.setConfirmedRequest(eventDto.getConfirmedRequest());
        }
        if (eventDto.getCreatedOn() != null) {
            event.setCreatedOn(eventDto.getCreatedOn());
        } else event.setCreatedOn(LocalDateTime.now());

        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());

        if (eventDto.getInitiatorId() != null) {
            event.setInitiatorId(eventDto.getInitiatorId());
        }
        event.setPaid(eventDto.getPaid());
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getPublishedOn() != null) {
            event.setPublishedOn(eventDto.getPublishedOn());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.getState() != null) {
            event.setState(eventDto.getState());
        } else event.setState(Status.PENDING);

        event.setTitle(eventDto.getTitle());

        if (eventDto.getViews() != null) {
            event.setViews(eventDto.getViews());
        }
        return event;
    }

    public List<EventFullDto> toEventFullDtoList(List<Event> events) {
        List<EventFullDto> listDto = new ArrayList<>();
        if (events.size() == 0) {
            return listDto;
        }
        for (Event event : events) {
            EventFullDto dto = toEventFullDto(event);
            listDto.add(dto);
        }
        return listDto;
    }

    public List<EventDto> toEventShortDtoList(List<Event> events) {
        List<EventDto> listDto = new ArrayList<>();
        if (events.size() == 0) {
            return listDto;
        }
        for (Event event : events) {
            EventDto dto = toEventDto(event);
            listDto.add(dto);
        }
        return listDto;
    }
}
