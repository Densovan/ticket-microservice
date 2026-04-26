package com.denkh.event.service;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.event.dto.EventRequest;

public interface EventService {

    ResponseErrorTemplate create(EventRequest request);
    ResponseErrorTemplate update(Long id, EventRequest request);
    ResponseErrorTemplate getById(Long id);
    ResponseErrorTemplate delete(Long id);
}