package ru.practicum.explorewhithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.ViewStatsDto;
import ru.practicum.explorewhithme.repository.UriHits;

import java.util.ArrayList;
import java.util.List;

@Component
public class UriHitsMapper {
    public ViewStatsDto toViewStats(UriHits uriHits) {
        ViewStatsDto viewStatsDto = new ViewStatsDto(uriHits.getApp(), uriHits.getUri(), Long.valueOf(uriHits.getHits()));
        return viewStatsDto;
    }

    public List<ViewStatsDto> toViewStats(List<UriHits> uriHitsList) {
        List<ViewStatsDto> list = new ArrayList<>();
        for (UriHits uri : uriHitsList) {
            ViewStatsDto viewStatsDto = toViewStats(uri);
            list.add(viewStatsDto);
        }
        return list;
    }
}
