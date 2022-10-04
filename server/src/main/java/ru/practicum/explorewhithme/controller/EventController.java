package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.client.StatsClient;
import ru.practicum.explorewhithme.dto.EventFullDto;
import ru.practicum.explorewhithme.dto.EventDto;
import ru.practicum.explorewhithme.dto.HitDto;
import ru.practicum.explorewhithme.mapper.EventMapper;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.model.Status;
import ru.practicum.explorewhithme.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final StatsClient statsClient;

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody EventDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        event.setInitiatorId(userId);
        Event saveEvent = eventService.save(event);
        log.info("Новое событие: " + saveEvent);
        return eventMapper.toEventFullDto(saveEvent);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEventWithFilter(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) Integer[] categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(required = false) Boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        List<Event> events = eventService.getEventsWithFilter(text, categories, paid, onlyAvailable, sort, rangeStart,
                rangeEnd, from, size);
        return eventMapper.toEventFullDtoList(events);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getPublishedEvent(@PathVariable long eventId, HttpServletRequest request) {
        log.info("Запрошено опубликованное событие id: " + eventId);
        sendHit(request);
        Event event = eventService.getPublishedEvent(eventId);
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventForUser(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Запрошено событие id: " + eventId);
        Event event = eventService.getEventForInitiator(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventFullDto> getEvents(@PathVariable long userId,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "20") int size) {
        log.info("Запрошены события пользователя id: " + userId);
        List<Event> events = eventService.getAllEventForInitiator(userId, from, size);
        return eventMapper.toEventFullDtoList(events);
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto updateEventForUser(@PathVariable long userId, @Valid @RequestBody EventDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        Event saveEvent = eventService.updateEventForUser(event, userId);
        log.info("Событие: " + saveEvent + "обновлено");
        return eventMapper.toEventFullDto(saveEvent);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancellation(@PathVariable long userId, @PathVariable long eventId) {
        Event canceledEvent = eventService.cancel(userId, eventId);
        log.info("Событие: " + canceledEvent + "отменено");
        return eventMapper.toEventFullDto(canceledEvent);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getEventWithFilterForAdmin(@RequestParam(required = false) Integer[] users,
                                                         @RequestParam(required = false) Status[] states,
                                                         @RequestParam(required = false) Integer[] categories,
                                                         @RequestParam(required = false) String rangeStart,
                                                         @RequestParam(required = false) String rangeEnd,
                                                         @RequestParam(required = false, defaultValue = "0") int from,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        List<Event> events = eventService.getEventWithFilterForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventMapper.toEventFullDtoList(events);
    }

    @PutMapping("/admin/events/{eventId}")
    public EventFullDto updateEventForAdmin(@PathVariable long eventId, @Valid @RequestBody EventDto eventDto) {
        eventDto.setId(eventId);
        Event event = eventMapper.toEvent(eventDto);
        Event saveEvent = eventService.updateEventForAdmin(event);
        log.info("Событие: id " + eventId + "обновлено");
        return eventMapper.toEventFullDto(saveEvent);
    }

    @PatchMapping("/admin/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        Event publishedEvent = eventService.publishedEvent(eventId);
        log.info("Событие: " + publishedEvent + "опубликовано");
        return eventMapper.toEventFullDto(publishedEvent);
    }

    @PatchMapping("/admin/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        Event rejectEvent = eventService.rejectEvent(eventId);
        log.info("Событие: " + rejectEvent + "отклонено");
        return eventMapper.toEventFullDto(rejectEvent);
    }

    private void sendHit(HttpServletRequest request) {
        HitDto hit = new HitDto("server", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        statsClient.sendHit(hit);
    }
}
