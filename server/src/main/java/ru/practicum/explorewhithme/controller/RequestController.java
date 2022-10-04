package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.RequestDto;
import ru.practicum.explorewhithme.mapper.RequestMapper;
import ru.practicum.explorewhithme.model.Request;
import ru.practicum.explorewhithme.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping("/users/{userId}/requests")
    public RequestDto saveRequest(@PathVariable long userId, @RequestParam long eventId) {
        Request request = requestService.save(userId, eventId);
        return requestMapper.toRequestDto(request);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        Request request = requestService.cancel(userId, requestId);
        return requestMapper.toRequestDto(request);
    }

    @GetMapping("/users/{userId}/requests")
    public List<RequestDto> getAllRequestForUser(@PathVariable long userId) {
        List<Request> requests = requestService.allUserRequests(userId);
        return requestMapper.toRequestDtoList(requests);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        List<Request> requests = requestService.getRequestsForEvent(userId, eventId);
        log.info("Запрос на участие пользователя id: " + userId + "в событии" + eventId);
        return requestMapper.toRequestDtoList(requests);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public RequestDto confirmUserRequests(@PathVariable long userId, @PathVariable long eventId,
                                          @PathVariable long requestId) {
        Request request = requestService.confirmUserRequests(userId, eventId, requestId);
        log.info("Запрос на участие пользователя id: " + userId + "в событии" + eventId + "подтверждён");
        return requestMapper.toRequestDto(request);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{requestId}/reject")
    public RequestDto rejectUserRequests(@PathVariable long userId, @PathVariable long eventId,
                                         @PathVariable long requestId) {
        Request request = requestService.rejectUserRequests(userId, eventId, requestId);
        log.info("Запрос на участие пользователя id: " + userId + "в событии" + eventId + "подтверждён");
        return requestMapper.toRequestDto(request);
    }
}
