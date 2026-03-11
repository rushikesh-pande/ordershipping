package com.shipping.monitoring.service;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Monitoring Enhancement: Business Metrics Service
 *
 * Central place to record domain-level metrics for ordershipping.
 * These appear in Prometheus and can be visualised in Grafana.
 *
 * Metrics registered:
 *  - ordershipping.operations.total      — counter per operation + status
 *  - ordershipping.active.gauge          — current in-flight operations
 *  - ordershipping.operation.duration    — summary timer
 *  - ordershipping.errors.total          — error counter per error type
 *  - ordershipping.kafka.events.total    — Kafka event counter
 */
@Service
public class BusinessMetricsService {

    private final MeterRegistry meterRegistry;

    // Gauge — currently active operations
    private final AtomicInteger activeOperations = new AtomicInteger(0);

    public BusinessMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        // Register gauge once
        Gauge.builder("ordershipping.active.operations", activeOperations, AtomicInteger::get)
             .description("Number of currently active ordershipping operations")
             .tag("service", "ordershipping")
             .register(meterRegistry);
    }

    /**
     * Record a successful operation completion.
     * @param operationType  e.g. "create", "update", "delete", "query"
     */
    public void recordSuccess(String operationType) {
        Counter.builder("ordershipping.operations.total")
               .tag("service", "ordershipping")
               .tag("operation", operationType)
               .tag("status", "success")
               .description("Total ordershipping operations by type and status")
               .register(meterRegistry)
               .increment();
    }

    /**
     * Record a failed operation.
     * @param operationType  e.g. "create", "update"
     * @param errorType      e.g. "validation", "database", "kafka"
     */
    public void recordFailure(String operationType, String errorType) {
        Counter.builder("ordershipping.errors.total")
               .tag("service", "ordershipping")
               .tag("operation", operationType)
               .tag("error_type", errorType)
               .description("Total ordershipping errors by operation and error type")
               .register(meterRegistry)
               .increment();
    }

    /**
     * Record a Kafka event published or consumed.
     * @param topic      Kafka topic name
     * @param direction  "published" or "consumed"
     */
    public void recordKafkaEvent(String topic, String direction) {
        Counter.builder("ordershipping.kafka.events.total")
               .tag("service", "ordershipping")
               .tag("topic", topic)
               .tag("direction", direction)
               .description("Total Kafka events for ordershipping")
               .register(meterRegistry)
               .increment();
    }

    /**
     * Record operation duration.
     * @param operationType  operation name
     * @param durationMs     elapsed milliseconds
     */
    public void recordDuration(String operationType, long durationMs) {
        meterRegistry.summary("ordershipping.operation.duration",
                "service", "ordershipping",
                "operation", operationType)
               .record(durationMs);
    }

    /** Mark one more in-flight operation. Call at start of operation. */
    public void incrementActive() { activeOperations.incrementAndGet(); }

    /** Mark one less in-flight operation. Call in finally block. */
    public void decrementActive() { activeOperations.decrementAndGet(); }
}
