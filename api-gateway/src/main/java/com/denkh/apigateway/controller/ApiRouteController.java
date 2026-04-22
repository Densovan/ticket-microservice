package com.denkh.apigateway.controller;

import com.denkh.apigateway.dto.RouteApiRequest;
import com.denkh.apigateway.dto.RouteApiResponse;
import com.denkh.apigateway.exception.ApiResponse;
import com.denkh.apigateway.service.ApiRouteService;
import com.denkh.apigateway.service.GateWayRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/routes",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class ApiRouteController {
    private final ApiRouteService apiRouteService;
    private final GateWayRouteService gateWayRouteService;


    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<Void>> refreshRoutes() {
        gateWayRouteService.refreshRoutes();
        return Mono.empty();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> create(@RequestBody RouteApiRequest apiRequest) {
        return apiRouteService.create(apiRequest)
                .map(ApiResponse::success);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> update(@PathVariable Long id, @RequestBody RouteApiRequest apiRequest) {
        return apiRouteService.update(id, apiRequest)
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> getById(@PathVariable Long id) {
        return apiRouteService.findById(id)
                .map(ApiResponse::success);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<List<RouteApiResponse>>> getAll() {
        return apiRouteService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }
}
