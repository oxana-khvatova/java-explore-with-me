package ru.practicum.explorewhithme.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.model.Request;
import ru.practicum.explorewhithme.model.Status;
import ru.practicum.explorewhithme.repository.RequestRepository;

import java.util.Objects;
import java.util.Optional;

public class RequestService {
    RequestRepository requestRepository;

    EventService eventService;

    @Autowired
    public RequestService(RequestRepository requestRepository, EventService eventService) {
        this.requestRepository = requestRepository;
        this.eventService = eventService;
    }

    public Request save(Long userId, Long eventId, Request request) {
        if (request.getId() != null) {
            throw new RuntimeException();
        }
        Event event = eventService.findById(eventId);
        if (!Objects.equals(event.getInitiatorId(), userId)
                && (event.getParticipantLimit() > event.getConfirmedRequest())) {
            if (!event.getRequestModeration()) {
                request.setStatus(Status.PUBLISHED);
            } else {
                request.setStatus(Status.PENDING);
            }
        } else {
            throw new RuntimeException();
        }
        return requestRepository.save(request);
    }

    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }
}
