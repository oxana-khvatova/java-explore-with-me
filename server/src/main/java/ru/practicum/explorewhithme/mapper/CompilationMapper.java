package ru.practicum.explorewhithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.CompilationDto;
import ru.practicum.explorewhithme.dto.EventDto;
import ru.practicum.explorewhithme.dto.NewCompilationDto;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.service.EventService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompilationMapper {
    private final EventService eventService;

    private final EventMapper eventMapper;

    public Compilation toCompilation(NewCompilationDto newCompilationDto) {
        List<Integer> eventsId = newCompilationDto.getEvents();
        Set<Event> events = new HashSet<>();
        for (Integer number : eventsId) {
            Event event = eventService.findById(Long.valueOf(number));
            events.add(event);
        }
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setEvents(events);
        return compilation;
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        List<Event> events = new ArrayList<>(compilation.getEvents());
        List<EventDto> eventsDto = eventMapper.toEventShortDtoList(events);
        CompilationDto compilationDto = new CompilationDto(eventsDto, compilation.getId(), compilation.getPinned(),
                compilation.getTitle());
        return compilationDto;
    }

    public List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        List<CompilationDto> listDto = new ArrayList<>();
        for (Compilation comp : compilations) {
            listDto.add(toCompilationDto(comp));
        }
        return listDto;
    }
}
