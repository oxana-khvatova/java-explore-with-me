package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.explorewhithme.model.EndpointHit;
import ru.practicum.explorewhithme.model.UriHits;
import ru.practicum.explorewhithme.repository.RepositoryStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceStats {
    RepositoryStats repositoryStats;

    @Autowired
    public ServiceStats(RepositoryStats repositoryStats) {
        this.repositoryStats = repositoryStats;
    }

    public void handleHit(EndpointHit endpointHit) {
        repositoryStats.save(endpointHit);
    }

    public List<UriHits> takeStates(String start, String end, String[] uris, Boolean unique) {
        List<UriHits> hits;
        if (unique) {
            hits = repositoryStats.countUniqueHits(start, end);
        } else {
            hits = repositoryStats.countHits(start, end);
        }
        if (uris.length == 0) {
            return hits;
        }
        List<String> listUris = Arrays.asList(uris);
        List<UriHits> hitsSortWithUris = new ArrayList<>();
        for (UriHits uri : hits) {
            if (listUris.contains(uri.getUri())) {
                hitsSortWithUris.add(uri);
            }
        }
        return hitsSortWithUris;
    }
}
