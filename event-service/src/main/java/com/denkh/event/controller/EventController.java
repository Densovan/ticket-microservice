package com.denkh.event.controller;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.event.dto.EventRequest;
import com.denkh.event.repository.EventRepository;
import com.denkh.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<ResponseErrorTemplate> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        log.info("Intercept create new event with req: {}", eventRequest);
        return ResponseEntity.ok(eventService.create(eventRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> getEventById(@PathVariable Long id) {
        log.info("Intercept get event by id: {}", id);
        return ResponseEntity.ok(eventService.getById(id));
    }

    @DeleteMapping
    public ResponseEntity<ResponseErrorTemplate> deleteEventById(@PathVariable Long id) {
        log.info("Intercept delete event with id: {}", id);
        return ResponseEntity.ok(eventService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        log.info("Intercept update event with id: {} and req: {}", id, eventRequest);
        return ResponseEntity.ok(eventService.update(id, eventRequest));
    }

}
