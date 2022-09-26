package ru.practicum.explorewhithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewhithme.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query(value =
            "SELECT '*' FROM Compilation WHERE pinned = TRUE"
    )
    List<Compilation> findPinnedCompilations(Pageable page);

    @Query(value =
            "SELECT '*' FROM Compilation WHERE pinned = FALSE"
    )
    List<Compilation> findCompilations(Pageable page);
}
