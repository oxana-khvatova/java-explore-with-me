package ru.practicum.explorewhithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.EndpointHitDto;
import ru.practicum.explorewhithme.dto.ViewStatsDto;
import ru.practicum.explorewhithme.mapper.EndpointHitMapper;
import ru.practicum.explorewhithme.mapper.UriHitsMapper;
import ru.practicum.explorewhithme.model.EndpointHit;
import ru.practicum.explorewhithme.repository.UriHits;
import ru.practicum.explorewhithme.service.ServiceStats;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatsController {
    ServiceStats serviceStats;
    EndpointHitMapper endpointHitMapper;
    UriHitsMapper uriHitsMapper;

    @Autowired
    public StatsController(ServiceStats serviceStats, EndpointHitMapper endpointHitMapper,
                           UriHitsMapper uriHitsMapper) {
        this.serviceStats = serviceStats;
        this.endpointHitMapper = endpointHitMapper;
        this.uriHitsMapper = uriHitsMapper;
    }

    @PostMapping("/hit")
    public void saveEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toEndpointHit(endpointHitDto);
        serviceStats.handleHit(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> takeStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam(required = false) String[] uris,
                                        @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        List<UriHits> hits = serviceStats.takeStats(start, end, uris, unique);
        return uriHitsMapper.toViewStats(hits);
    }


}
