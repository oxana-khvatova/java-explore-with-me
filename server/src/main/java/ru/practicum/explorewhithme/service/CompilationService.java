package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.repository.CompilationRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventService eventService;

    public Compilation saveCompilation(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        Set<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
    }

    public void deleteEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilation.setEvents(events);
    }

    public void pinCompilation(Long compId) {
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.TRUE);
    }

    public void deletePinCompilation(Long compId) {
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.FALSE);
    }

    public void deleteCompilation(Long compId) {
        Compilation compilation = findById(compId);
        compilationRepository.delete(compilation);
    }

    public Compilation findById(Long id) {
        Optional<Compilation> event = compilationRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else throw new RuntimeException("Compilation id= " + id + " not found");
    }

    public List<Compilation> findCompilations(Boolean pinned, Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        if (pinned.equals(Boolean.TRUE)) {
            return compilationRepository.findPinnedCompilations(page);
        } else {
            return compilationRepository.findCompilations(page);
        }
    }
}
