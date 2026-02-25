package com.shipping.service;

import com.shipping.dto.ScheduleDeliveryRequest;
import com.shipping.kafka.DeliveryEventProducer;
import com.shipping.model.DeliverySchedule;
import com.shipping.model.DeliveryStatus;
import com.shipping.model.DeliveryType;
import com.shipping.repository.DeliveryScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryScheduleService {

    private final DeliveryScheduleRepository scheduleRepository;
    private final DeliveryEventProducer eventProducer;

    @Transactional
    public DeliverySchedule scheduleDelivery(ScheduleDeliveryRequest request) {
        log.info("Scheduling delivery for order: {} with type: {}", 
            request.getOrderId(), request.getDeliveryType());

        DeliverySchedule schedule = new DeliverySchedule();
        schedule.setOrderId(request.getOrderId());
        schedule.setCustomerId(request.getCustomerId());
        schedule.setDeliveryType(request.getDeliveryType());
        schedule.setSpecialInstructions(request.getSpecialInstructions());
        schedule.setIsFlexible(request.getIsFlexible() != null ? request.getIsFlexible() : false);
        schedule.setStatus(DeliveryStatus.SCHEDULED);

        // Set scheduled date based on delivery type
        if (request.getDeliveryType() == DeliveryType.SAME_DAY) {
            schedule.setScheduledDate(LocalDateTime.now());
            schedule.setTimeSlot(request.getTimeSlot() != null ? request.getTimeSlot() : "18:00-21:00");
        } else if (request.getDeliveryType() == DeliveryType.NEXT_DAY) {
            schedule.setScheduledDate(LocalDateTime.now().plusDays(1));
            schedule.setTimeSlot(request.getTimeSlot() != null ? request.getTimeSlot() : "09:00-12:00");
        } else if (request.getDeliveryType() == DeliveryType.EXPRESS) {
            schedule.setScheduledDate(LocalDateTime.now().plusDays(1));
        } else if (request.getDeliveryType() == DeliveryType.SCHEDULED) {
            schedule.setScheduledDate(request.getScheduledDate());
            schedule.setTimeSlot(request.getTimeSlot());
        } else {
            schedule.setScheduledDate(LocalDateTime.now().plusDays(3)); // Standard delivery
        }

        DeliverySchedule savedSchedule = scheduleRepository.save(schedule);

        // Publish Kafka event
        eventProducer.publishDeliveryScheduled(savedSchedule);

        log.info("Delivery scheduled: {} for date: {}", 
            savedSchedule.getScheduleId(), savedSchedule.getScheduledDate());

        return savedSchedule;
    }

    public DeliverySchedule getScheduleByOrderId(String orderId) {
        return scheduleRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Schedule not found for order: " + orderId));
    }

    public List<DeliverySchedule> getCustomerSchedules(String customerId) {
        return scheduleRepository.findByCustomerId(customerId);
    }

    @Transactional
    public DeliverySchedule updateStatus(String scheduleId, DeliveryStatus status) {
        DeliverySchedule schedule = scheduleRepository.findByScheduleId(scheduleId)
            .orElseThrow(() -> new RuntimeException("Schedule not found: " + scheduleId));

        schedule.setStatus(status);
        return scheduleRepository.save(schedule);
    }
}

