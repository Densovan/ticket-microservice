package com.denkh.apigateway.service.impl;

import com.denkh.apigateway.service.GateWayRouteService;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class GateWayRouteServiceImpl implements GateWayRouteService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GateWayRouteServiceImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void refreshRoutes() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
