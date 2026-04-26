package com.denkh.event.service;

import com.denkh.common.constant.ApiConstant;
import com.denkh.common.dto.EmptyObject;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.event.dto.EventRequest;
import com.denkh.event.dto.EventResponse;
import com.denkh.event.entity.Event;
import com.denkh.event.mapper.EventMapper;
import com.denkh.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public ResponseErrorTemplate create(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        event.setEventDate(LocalDateTime.now());
        eventRepository.save(event);
        EventResponse response = eventMapper.toResponse(event);
        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                response,
                false
        );
    }

    @Override
    public ResponseErrorTemplate update(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        event.setEventDate(request.getEventDate());
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventType(request.getEventType());
        event.setStatus(request.getStatus());
        event.setBasePrice(request.getBasePrice());
        Event updatedEvent = eventRepository.save(event);
        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                eventMapper.toResponse(updatedEvent),
                false
        );
    }

    @Override
    public ResponseErrorTemplate getById(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            return new ResponseErrorTemplate(
                    ApiConstant.DATA_NOT_FOUND.getDescription(),
                    ApiConstant.DATA_NOT_FOUND.getKey(),
                    new EmptyObject(),
                    true);
        }
        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                eventMapper.toResponse(event.get()),
                false
        );
    }

    @Override
    public ResponseErrorTemplate delete(Long id) {
        ResponseErrorTemplate event = getById(id);
        eventRepository.deleteById(id);
        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                new EmptyObject(),
                false
        );
    }
}
