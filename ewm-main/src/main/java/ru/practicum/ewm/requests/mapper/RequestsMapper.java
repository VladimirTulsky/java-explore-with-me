package ru.practicum.ewm.requests.mapper;

import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.model.ParticipationRequest;

public class RequestsMapper {
    public static RequestDto toRequestDto(ParticipationRequest participationRequest) {
        return new RequestDto(participationRequest.getId(),
                participationRequest.getCreated(),
                participationRequest.getEvent().getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getStatus());
    }
}
