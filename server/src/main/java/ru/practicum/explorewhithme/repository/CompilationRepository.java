package ru.practicum.explorewhithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewhithme.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long>, JpaSpecificationExecutor {
    @Query(
            "SELECT c FROM Compilation AS c WHERE c.pinned = TRUE"
    )
    List<Compilation> findPinnedCompilations(Pageable page);

    @Query(
            "SELECT c FROM Compilation AS c WHERE c.pinned = FALSE"
    )
    List<Compilation> findCompilations(Pageable page);
}
