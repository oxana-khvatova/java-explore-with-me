package ru.practicum.explorewhithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewhithme.dto.CompilationDto;
import ru.practicum.explorewhithme.dto.NewCompilationDto;
import ru.practicum.explorewhithme.mapper.CompilationMapper;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping
public class CompilationController {
    CompilationService compilationService;

    CompilationMapper compilationMapper;

    @Autowired
    public CompilationController(CompilationService compilationService, CompilationMapper compilationMapper) {
        this.compilationService = compilationService;
        this.compilationMapper = compilationMapper;
    }

    @PostMapping("/admin/compilations")
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);
        Compilation saveCompilation = compilationService.saveCompilation(compilation);
        log.info("Новая подборка: " + newCompilationDto);
        return compilationMapper.toCompilationDto(saveCompilation);
    }
}
