package ru.practicum.explorewhithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.RequestDto;
import ru.practicum.explorewhithme.model.Request;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    public RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setEvent(request.getEventId());
        requestDto.setRequester(request.getRequesterId());
        requestDto.setStatus(request.getStatus());
        requestDto.setCreated(request.getCreated());
        return requestDto;
    }

    public List<RequestDto> toRequestDtoList(List<Request> requests) {
        List<RequestDto> listDto = new ArrayList<RequestDto>();
        if (requests.size() == 0) {
            return listDto;
        }
        for (Request request : requests) {
            RequestDto dto = toRequestDto(request);
            listDto.add(dto);
        }
        return listDto;
    }
}
