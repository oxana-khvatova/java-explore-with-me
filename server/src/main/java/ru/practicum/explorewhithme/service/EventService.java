package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    EventRepository eventRepository;
    UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Event save(Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new RuntimeException();
        }
        return eventRepository.save(event);
    }

    public Event upDate(Event event) {
        Event eventUpDate = new Event();
        eventUpDate.setState(Status.PENDING);
        if (event.getEventDate() != null) {
            if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new RuntimeException();
            }
            eventUpDate.setEventDate(event.getEventDate());
        }
        if (event.getAnnotation() != null) {
            eventUpDate.setAnnotation(event.getAnnotation());
        }
        if (event.getCategoryId() != null) {
            eventUpDate.setCategoryId(event.getCategoryId());
        }
        if (event.getDescription() != null) {
            eventUpDate.setDescription(event.getDescription());
        }
        if (event.getPaid() != null) {
            eventUpDate.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            eventUpDate.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getTitle() != null) {
            eventUpDate.setTitle(event.getTitle());
        }
        return eventRepository.save(eventUpDate);
    }

    public void checkPermission(Event eventUpDate, Long userId) {
        if (!((eventUpDate.getState().equals(Status.CANCELED) || (eventUpDate.getState().equals(Status.PENDING)))
                || !eventUpDate.getRequestModeration().equals(Boolean.TRUE))) { //Проверка пользователя для разрешения правок
            throw new RuntimeException();
        }
    }

    public Event updateEventForUser(Event event, Long userId) {
        checkPermission(event, userId);
        return upDate(event);
    }

    public Event updateEventForAdmin(Event event, Long eventId) {
        findById(eventId); // проверка, что событие существует
        return upDate(event);
    }

    public Event publishedEvent(Long eventId) {
        Event event = findById(eventId);
        event.setState(Status.PUBLISHED);
        return event;
    }

    public Event rejectEvent(Long eventId) {
        Event event = findById(eventId);
        event.setState(Status.CANCELED);
        return event;
    }

    public Event cancel(Long userId, Long eventId) {
        Event event = findById(eventId);
        if (!event.getRequestModeration() || !Objects.equals(event.getInitiatorId(), userId)) {
            throw new RuntimeException();
        }
        event.setState(Status.CANCELED);
        return event;
    }

    public Event findById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else throw new RuntimeException("event id= " + id + " not found");
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

    public Event getPublishedEvent (Long idEvent){
        Event event = findById(idEvent);
        if(event.getState().equals(Status.PUBLISHED)){
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

//    private Specification<Event> eventIsAvailable() {
//        return (root, query, builder) -> builder.lessThan(root.get("confirmedRequest"), root.get("participantLimit"));
//    }

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
        Specification<Event> containsText = text == null ? null : eventDescriptionContainsText(text)
                .or(eventAnnotationContainsText(text));
        Specification<Event> isPaid = paid == null ? null : eventIsPaid(paid);
//        Specification<Event> isAvailable = onlyAvailable == null || !onlyAvailable ? null : eventIsAvailable();
        Specification<Event> hasCategory = categories == null ? null : eventInCategories(categories);
        Specification<Event> dateRange = (rangeStart == null || rangeEnd == null) ?
                eventsAfter() : eventsBetween(
                LocalDateTime.parse(rangeStart),
                LocalDateTime.parse(rangeEnd));

        Specification<Event> spec = Specification.where(containsText)
                .and(isPaid)
                .and(hasCategory)
//                .and(isAvailable);
                .and(dateRange);
        return eventRepository.findAll(spec, page).toList();
    }
}
