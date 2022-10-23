package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.explorewhithme.exception.AccessException;
import ru.practicum.explorewhithme.exception.DateException;
import ru.practicum.explorewhithme.exception.EventNotFoundException;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.repository.EventRepository;
import ru.practicum.explorewhithme.repository.UserRepository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Event save(Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DateException("Impossible create event in this time period");
        }
        event.setCreatedOn(LocalDateTime.now());
        if (event.getConfirmedRequest() == null) {
            event.setConfirmedRequest(0L);
        }
        if (event.getParticipantLimit() == null) {
            event.setParticipantLimit(0L);
        }
        return eventRepository.save(event);
    }

    public Event patchEvent(Event eventToDate, Event eventPatch) {
        eventToDate.setState(Status.PENDING);
        if (eventPatch.getEventDate() != null) {
            if (eventPatch.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new DateException("Impossible create event in this time period");
            }
            eventToDate.setEventDate(eventPatch.getEventDate());
        }
        if (eventPatch.getAnnotation() != null) {
            eventToDate.setAnnotation(eventPatch.getAnnotation());
        }
        if (eventPatch.getCategoryId() != null) {
            eventToDate.setCategoryId(eventPatch.getCategoryId());
        }
        if (eventPatch.getDescription() != null) {
            eventToDate.setDescription(eventPatch.getDescription());
        }
        if (eventPatch.getPaid() != null) {
            eventToDate.setPaid(eventPatch.getPaid());
        }
        if (eventPatch.getParticipantLimit() != null) {
            eventToDate.setParticipantLimit(eventPatch.getParticipantLimit());
        }
        if (eventPatch.getTitle() != null) {
            eventToDate.setTitle(eventPatch.getTitle());
        }
        return eventRepository.save(eventToDate);
    }

    public void checkPermission(Event event, Long userId) {
        if (!((event.getState().equals(Status.CANCELED) || (event.getState().equals(Status.PENDING)))
                || !event.getRequestModeration().equals(Boolean.TRUE))) { //Проверка пользователя для разрешения правок
            throw new AccessException("У пользователя нет доступа");
        }
    }

    public Event updateEventForUser(Event patchEvent, Long userId) {
        Event event = findById(patchEvent.getId());
        checkPermission(event, userId);
        return patchEvent(event, patchEvent);
    }

    public Event updateEventForAdmin(Event patchEvent) {
        Event event = findById(patchEvent.getId());
        return patchEvent(event, patchEvent);
    }

    public Event publishedEvent(Long eventId) {
        Event event = findById(eventId);
        event.setState(Status.PUBLISHED);
        return eventRepository.save(event);
    }

    public Event rejectEvent(Long eventId) {
        Event event = findById(eventId);
        event.setState(Status.CANCELED);
        return eventRepository.save(event);
    }

    public Event cancel(Long userId, Long eventId) {
        Event event = findById(eventId);
        if (!event.getRequestModeration() || !Objects.equals(event.getInitiatorId(), userId)) {
            throw new AccessException("Не возможно изменить статус");
        }
        event.setState(Status.CANCELED);
        return eventRepository.save(event);
    }

    public Event findById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else throw new EventNotFoundException("event id= " + id + " not found");
    }

    public List<Event> getAllEventForInitiator(Long idInitiator, int from, int size) {
        userRepository.findById(idInitiator); //проверка, что пользователь существует
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        return eventRepository.findByInitiatorId(idInitiator, page);
    }

    public Event getEventForInitiator(Long idInitiator, Long idEvent) {
        Event event = findById(idEvent);
        if (!Objects.equals(idInitiator, event.getInitiatorId())) {
            throw new RuntimeException();
        }
        return event;
    }

    public Event getPublishedEvent(Long idEvent) {
        Event event = findById(idEvent);
        if (!event.getState().equals(Status.PUBLISHED)) {
            throw new RuntimeException();
        }
        return findById(idEvent);
    }

    private Specification<Event> eventDescriptionContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("description"),
                MessageFormat.format("%{0}%", text));
    }

    private Specification<Event> eventAnnotationContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("annotation"),
                MessageFormat.format("%{0}%", text));
    }

    private Specification<Event> eventIsPaid(Boolean paid) {
        return (root, query, builder) -> builder.equal(root.get("paid"), paid);
    }

    private Specification<Event> eventIsAvailable() {
        return (root, query, builder) -> builder.lessThan(root.get("confirmedRequest"), root.get("participantLimit"));
    }

    private Specification<Event> eventsBetween(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, builder) -> builder.between(root.get("eventDate"), rangeStart, rangeEnd);
    }

    private Specification<Event> eventsAfter() {
        return (root, query, builder) -> builder.between(root.get("eventDate"), LocalDateTime.now(),
                LocalDateTime.of(3000, 12, 12, 15, 15, 15));
    }

    private Specification<Event> eventInCategories(Integer[] categories) {
        return (root, query, builder) -> root.get("categoryId").in((Object[]) categories);
    }

    private Specification<Event> eventInUsers(Integer[] users) {
        return (root, query, builder) -> root.get("initiatorId").in((Object[]) users);
    }

    private Specification<Event> eventInStates(Status[] states) {
        return (root, query, builder) -> root.get("state").in((Object[]) states);
    }

    public List<Event> getEventsWithFilter(String text, Integer[] categories, Boolean paid, Boolean onlyAvailable,
                                           String sort, String rangeStart, String rangeEnd, int from, int size) {
        if (sort == null) {
            throw new RuntimeException();
        }
        Pageable page;
        if (sort.equals("EVENT_DATE")) {
            page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
        } else if (sort.equals("VIEWS")) {
            page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "views"));
        } else {
            throw new RuntimeException();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Specification<Event> containsText = text == null ? null : eventDescriptionContainsText(text)
                .or(eventAnnotationContainsText(text));
        Specification<Event> isPaid = paid == null ? null : eventIsPaid(paid);
        Specification<Event> isAvailable = onlyAvailable == null || !onlyAvailable ? null : eventIsAvailable();
        Specification<Event> hasCategory = categories == null ? null : eventInCategories(categories);
        Specification<Event> dateRange = (rangeStart == null || rangeEnd == null) ?
                eventsAfter() : eventsBetween(
                LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter));

        Specification<Event> spec = Specification.where(containsText)
                .and(isPaid)
                .and(hasCategory)
                .and(isAvailable)
                .and(dateRange);
        return eventRepository.findAll(spec, page).toList();
    }

    public List<Event> getEventWithFilterForAdmin(Integer[] users, Status[] states, Integer[] categories,
                                                  String rangeStart, String rangeEnd, int from, int size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
        Specification<Event> hasCategory = categories == null ? null : eventInCategories(categories);
        Specification<Event> hasUsers = users == null ? null : eventInUsers(users);
        Specification<Event> hasStates = states == null ? null : eventInStates(states);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Specification<Event> dateRange = (rangeStart == null || rangeEnd == null) ?
                eventsAfter() : eventsBetween(
                LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter));
        Specification<Event> spec = Specification.where(hasCategory)
                .and(hasUsers)
                .and(hasStates)
                .and(dateRange);
        return eventRepository.findAll(spec, page).toList();
    }
}
