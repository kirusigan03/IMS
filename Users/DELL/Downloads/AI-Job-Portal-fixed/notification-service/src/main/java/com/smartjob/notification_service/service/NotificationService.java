package com.smartjob.notification_service.service;

import com.smartjob.notification_service.entity.Notification;
import com.smartjob.notification_service.event.ApplicationStatusChangedEvent;
import com.smartjob.notification_service.event.ApplicationSubmittedEvent;
import com.smartjob.notification_service.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository
            notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository =
                notificationRepository;
    }

    @KafkaListener(
            topics = "application-submitted",
            groupId = "notification-service"
    )
    public void handleApplicationSubmitted(
            ApplicationSubmittedEvent event
    ) {

        log.info(
                "Application submitted event received: {}",
                event.getApplicationId()
        );

        Notification notification =
                new Notification(
                        event.getCandidateId(),
                        "Application Submitted",
                        "Your application has been submitted successfully."
                );

        notificationRepository.save(
                notification
        );
    }

    @KafkaListener(
            topics = "application-status-changed",
            groupId = "notification-service",
            containerFactory = "statusChangedKafkaListenerContainerFactory"
    )
    public void handleApplicationStatusChanged(
            ApplicationStatusChangedEvent event
    ) {

        log.info(
                "Application status changed: {} -> {}",
                event.getApplicationId(),
                event.getNewStatus()
        );

        String message =
                "Your application status has been updated to "
                        + event.getNewStatus();

        Notification notification =
                new Notification(
                        event.getCandidateId(),
                        "Application Status Updated",
                        message
                );

        notificationRepository.save(
                notification
        );
    }

    public List<Notification> getUserNotifications(
            Long userId
    ) {

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(
                        userId
                );
    }

    public void markAsRead(Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                )
                        );

        notification.setRead(true);

        notificationRepository.save(
                notification
        );
    }
}
