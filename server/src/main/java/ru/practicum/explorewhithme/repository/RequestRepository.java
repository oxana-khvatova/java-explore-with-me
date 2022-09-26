package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequesterId(Long initiatorId);

    List<Request> findByEventId(Long eventId);
}
