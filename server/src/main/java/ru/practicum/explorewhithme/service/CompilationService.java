package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.repository.CompilationRepository;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationService {
    CompilationRepository compilationRepository;
    EventService eventService;

    @Autowired
    public CompilationService(CompilationRepository compilationRepository, EventService eventService) {
        this.compilationRepository = compilationRepository;
        this.eventService = eventService;
    }

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

    public void pinCompilation(Long compId){
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.TRUE);
    }

    public void deletePinCompilation(Long compId){
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.FALSE);
    }

    public void deleteCompilation(Long compId){
        Compilation compilation = findById(compId);
        compilationRepository.delete(compilation);
    }

    public Compilation findById(Long id) {
        Optional<Compilation> event = compilationRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else throw new RuntimeException("Compilation id= " + id + " not found");
    }
}
