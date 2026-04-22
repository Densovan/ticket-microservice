package com.denkh.apigateway.service;

import com.denkh.apigateway.dto.RouteApiRequest;
import com.denkh.apigateway.dto.RouteApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {
    Mono<RouteApiResponse> create(RouteApiRequest request);
    Mono<RouteApiResponse> update(Long id, RouteApiRequest request);
    Mono<RouteApiResponse> findById(Long id);
    Flux<RouteApiResponse> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Void> deleteAll();
}
