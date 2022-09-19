package ru.practicum.explorewhithme.mapper;

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
public class EventMapper {
    CategoryService categoryService;
    UserService userService;

    CategoryMapper categoryMapper;

    UserMapper userMapper;

    @Autowired
    public EventMapper(CategoryService categoryService, UserService userService,
                       CategoryMapper categoryMapper, UserMapper userMapper) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

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

    public Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(event.getId());
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

//        if (eventDto.getInitiator() != null) {
//            event.setInitiatorId(eventDto.getInitiator().getId());
//        }
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

    public List<EventFullDto> toEventDtoList(List<Event> events) {
        List<EventFullDto> listDto = new ArrayList<EventFullDto>();
        if (events.size() == 0) {
            return listDto;
        }
        for (Event event : events) {
            EventFullDto dto = toEventFullDto(event);
            listDto.add(dto);
        }
        return listDto;
    }
}
