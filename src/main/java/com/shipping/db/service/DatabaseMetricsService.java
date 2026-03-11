package com.shipping.db.service;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Database Optimisation Enhancement: Database Metrics Service
 *
 * Tracks cache and query performance metrics for ordershipping.
 * Exposed to Prometheus via /actuator/prometheus.
 *
 * Metrics:
 *  - ordershipping_cache_hits_total       — Redis cache hits
 *  - ordershipping_cache_misses_total     — Redis cache misses (DB queries)
 *  - ordershipping_db_queries_total       — Total DB queries by type
 *  - ordershipping_db_slow_queries_total  — Queries above 500ms
 *  - ordershipping_connection_pool_active — HikariCP active connections
 */
@Service
public class DatabaseMetricsService {

    private final MeterRegistry meterRegistry;
    private final AtomicLong activeConnections = new AtomicLong(0);

    public DatabaseMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        Gauge.builder("ordershipping.connection.pool.active", activeConnections, AtomicLong::get)
             .description("Active HikariCP connections for ordershipping")
             .tag("service", "ordershipping")
             .register(meterRegistry);
    }

    public void recordCacheHit(String cacheName) {
        Counter.builder("ordershipping.cache.hits.total")
               .tag("service", "ordershipping").tag("cache", cacheName)
               .description("Redis cache hits for ordershipping")
               .register(meterRegistry).increment();
    }

    public void recordCacheMiss(String cacheName) {
        Counter.builder("ordershipping.cache.misses.total")
               .tag("service", "ordershipping").tag("cache", cacheName)
               .description("Redis cache misses for ordershipping (DB fallback)")
               .register(meterRegistry).increment();
    }

    public void recordDbQuery(String queryType) {
        Counter.builder("ordershipping.db.queries.total")
               .tag("service", "ordershipping").tag("type", queryType)
               .description("DB queries for ordershipping")
               .register(meterRegistry).increment();
    }

    public void recordSlowQuery(String queryType, long ms) {
        Counter.builder("ordershipping.db.slow.queries.total")
               .tag("service", "ordershipping").tag("type", queryType)
               .description("DB queries exceeding 500ms for ordershipping")
               .register(meterRegistry).increment();
        meterRegistry.summary("ordershipping.db.query.duration",
                "service", "ordershipping", "type", queryType).record(ms);
    }

    public void setActiveConnections(long count) {
        activeConnections.set(count);
    }
}
