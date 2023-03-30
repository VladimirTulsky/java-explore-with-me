package ru.practicum.ewm.events.mapper;

import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventMapper {

    public static Event toEventFromCreateDto(User initiator, Category category, CreateEventDto createEventDto) {
        return new Event(null,
                createEventDto.getAnnotation(),
                category,
                LocalDateTime.now(),
                createEventDto.getDescription(),
                createEventDto.getEventDate(),
                initiator,
                createEventDto.getLocation(),
                createEventDto.getPaid(),
                createEventDto.getParticipantLimit(),
                LocalDateTime.now(),
                createEventDto.getRequestModeration(),
                EventState.PENDING,
                createEventDto.getTitle());
    }

    public static FullEventDto toFullEventDto(Event event) {

        return new FullEventDto(event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                null,
                event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getDescription(),
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getRequestModeration(),
                event.getEventState().toString(),
                event.getTitle(),
                0L);
    }

    public static ShortEventDto toShortEventDto(Event event) {
        return new ShortEventDto(event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                null,
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                0L);
    }

    public static void toEventFromUpdateRequestDto(Event event,
                                                   EventUpdateRequestDto eventUpdateRequestDto) {
        if (Objects.equals(eventUpdateRequestDto.getStateAction(), UserActionState.CANCEL_REVIEW.name())) {
            event.setEventState(EventState.CANCELED);
        }
        if (Objects.equals(eventUpdateRequestDto.getStateAction(), UserActionState.SEND_TO_REVIEW.name())) {
            event.setEventState(EventState.PENDING);
        }
        if (eventUpdateRequestDto.getAnnotation() != null) {
            event.setAnnotation(eventUpdateRequestDto.getAnnotation());
        }
        if (eventUpdateRequestDto.getDescription() != null) {
            event.setDescription(eventUpdateRequestDto.getDescription());
        }
        if (eventUpdateRequestDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventUpdateRequestDto.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (eventUpdateRequestDto.getPaid() != null) {
            event.setPaid(eventUpdateRequestDto.getPaid());
        }
        if (eventUpdateRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateRequestDto.getParticipantLimit());
        }
        if (eventUpdateRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdateRequestDto.getRequestModeration());
        }
        if (eventUpdateRequestDto.getTitle() != null) {
            event.setTitle(eventUpdateRequestDto.getTitle());
        }
    }

    public static ShortEventDto toShortFromFull(FullEventDto fullEventDto) {
        return new ShortEventDto(fullEventDto.getId(),
                fullEventDto.getAnnotation(),
                fullEventDto.getCategory(),
                fullEventDto.getConfirmedRequests(),
                fullEventDto.getEventDate(),
                fullEventDto.getInitiator(),
                fullEventDto.getPaid(),
                fullEventDto.getTitle(),
                fullEventDto.getViews());
    }
}
