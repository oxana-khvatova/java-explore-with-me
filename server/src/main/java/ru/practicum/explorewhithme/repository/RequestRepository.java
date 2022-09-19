package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
