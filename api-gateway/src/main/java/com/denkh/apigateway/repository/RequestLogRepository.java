package com.denkh.apigateway.repository;

import com.denkh.apigateway.entity.RequestLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RequestLogRepository extends ReactiveCrudRepository<RequestLog, Long> {
}