package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation,Long> {
}
