package ru.practicum.explorewhithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.EventFullDto;
import ru.practicum.explorewhithme.dto.EventDto;
import ru.practicum.explorewhithme.mapper.EventMapper;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class EventController {
    EventService eventService;
    EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody EventDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        event.setInitiatorId(userId);
        Event saveEvent = eventService.save(event);
        log.info("Новое событие: " + saveEvent);
        return eventMapper.toEventFullDto(saveEvent);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Запрошено событие id: " + eventId);
        Event event = eventService.getEventForInitiator(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventFullDto> getEvent(@PathVariable long userId,
                                 @RequestParam(required = false, defaultValue = "0") int from,
                                 @RequestParam(required = false, defaultValue = "20") int size) {
        log.info("Запрошены события пользователя id: " + userId);
        List<Event> events = eventService.getAllEventForInitiator(userId, from, size);
        return eventMapper.toEventDtoList(events);
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto upDate(@PathVariable long userId, @Valid @RequestBody EventDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        Event saveEvent = eventService.upDate(event, userId);
        log.info("Событие: " + saveEvent + "обновлено");
        return eventMapper.toEventFullDto(saveEvent);
    }
}
