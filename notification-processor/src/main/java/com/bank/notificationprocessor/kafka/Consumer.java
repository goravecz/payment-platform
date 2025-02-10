package com.bank.notificationprocessor.kafka;

import com.bank.notificationprocessor.dto.Notification;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

  private static final String NOTIFICATIONS = "notifications";
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(Consumer.class);

  @KafkaListener(topics = NOTIFICATIONS)
  public void consumeNotifications(final Notification notification) {
    LOG.info("Consumed notification: {}", notification);
  }
}
