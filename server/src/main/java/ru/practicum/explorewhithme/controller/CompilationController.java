package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.CompilationDto;
import ru.practicum.explorewhithme.dto.NewCompilationDto;
import ru.practicum.explorewhithme.mapper.CompilationMapper;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.service.CompilationService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompilationController {
    private final CompilationService compilationService;

    private final CompilationMapper compilationMapper;

    @PostMapping("/admin/compilations")
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);
        Compilation saveCompilation = compilationService.saveCompilation(compilation);
        log.info("Новая подборка: " + newCompilationDto);
        return compilationMapper.toCompilationDto(saveCompilation);
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
        log.info("Событие id " + eventId + " добавлено в подборку id " + compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventToCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.deleteEventToCompilation(compId, eventId);
        log.info("Событие id " + eventId + " удалено в подборку id " + compId);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        compilationService.pinCompilation(compId);
        log.info("Подборка id " + compId + " закреплена");
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void deletePinCompilation(@PathVariable long compId) {
        compilationService.deletePinCompilation(compId);
        log.info("Подборка id " + compId + " откреплена");
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        compilationService.deleteCompilation(compId);
        log.info("Подборка id " + compId + " удалена");
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto findCompilationById(@PathVariable long compId) {
        Compilation compilation = compilationService.findById(compId);
        log.info("Подборка id " + compId);
        return compilationMapper.toCompilationDto(compilation);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> findCompilations(@RequestParam(required = false) boolean pinned,
                                           @RequestParam(required = false, defaultValue = "0") int from,
                                           @RequestParam(required = false, defaultValue = "20") int size) {
        List<Compilation> compilations = compilationService.findCompilations(pinned, from, size);
        log.info("Подборка compilations");
        return compilationMapper.toCompilationDtoList(compilations);
    }
}
