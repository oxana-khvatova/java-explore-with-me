package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.model.Compilation;
import ru.practicum.explorewhithme.repository.CompilationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationService {
    CompilationRepository compilationRepository;

    @Autowired
    public CompilationService(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    public Compilation saveCompilation(Compilation compilation) {
        return compilationRepository.save(compilation);
    }
}
