package com.shipping.controller;

import com.shipping.dto.ScheduleDeliveryRequest;
import com.shipping.model.DeliverySchedule;
import com.shipping.service.DeliveryScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryScheduleService deliveryScheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<DeliverySchedule> scheduleDelivery(@Valid @RequestBody ScheduleDeliveryRequest request) {
        DeliverySchedule schedule = deliveryScheduleService.scheduleDelivery(request);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliverySchedule> getScheduleByOrderId(@PathVariable String orderId) {
        DeliverySchedule schedule = deliveryScheduleService.getScheduleByOrderId(orderId);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<DeliverySchedule>> getCustomerSchedules(@PathVariable String customerId) {
        List<DeliverySchedule> schedules = deliveryScheduleService.getCustomerSchedules(customerId);
        return ResponseEntity.ok(schedules);
    }
}

