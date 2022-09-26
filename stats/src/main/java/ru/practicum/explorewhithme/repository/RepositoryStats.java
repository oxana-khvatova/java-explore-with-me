package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewhithme.model.EndpointHit;
import ru.practicum.explorewhithme.model.UriHits;

import java.util.List;

public interface RepositoryStats extends JpaRepository<EndpointHit, Long> {
    @Query(value =
            "SELECT app, uri, COUNT(ip) AS hits FROM EndpointHit WHERE timestamp >= :start AND timestamp < :end GROUP BY app, uri"
    )
    List<UriHits> countHits(@Param("start") String start, @Param("end") String end);

    @Query(value =
            "SELECT app, uri, COUNT(DISTINCT ip) AS hits FROM  EndpointHit WHERE timestamp >= :start AND timestamp < :end GROUP BY app, uri"
    )
    List<UriHits> countUniqueHits(@Param("start") String start, @Param("end") String end);
}
