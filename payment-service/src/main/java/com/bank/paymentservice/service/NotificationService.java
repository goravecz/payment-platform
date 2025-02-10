package com.bank.paymentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.bank.paymentservice.dto.Notification;

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);
    private static final String NOTIFICATION_TOPIC = "notifications";

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public NotificationService(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Notification notification) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(NOTIFICATION_TOPIC, notification)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        LOG.error("Failed to send notification", ex);
                        throw new RuntimeException("Failed to send notification", ex);
                    } else {
                        LOG.info("Notification sent to topic: {} with payload: {}",
                            result.getProducerRecord().topic(),
                            result.getProducerRecord().value());
                    }
                });
            return null;
        });
    }
}
