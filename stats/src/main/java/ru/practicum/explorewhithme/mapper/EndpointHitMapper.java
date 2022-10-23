package ru.practicum.explorewhithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.EndpointHitDto;
import ru.practicum.explorewhithme.model.EndpointHit;

@Component
public class EndpointHitMapper {
    public EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(endpointHitDto.getApp());
        endpointHit.setUri(endpointHitDto.getUri());
        endpointHit.setIp(endpointHitDto.getIp());
        endpointHit.setTimestamp(endpointHitDto.getTimestamp());
        return endpointHit;
    }
}
