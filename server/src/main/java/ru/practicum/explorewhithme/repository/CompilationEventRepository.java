package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.CompilationEvent;

public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
}
