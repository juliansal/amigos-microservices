package org.happykraken.notifications;

import lombok.AllArgsConstructor;
import org.happykraken.clients.notifications.NotificationsRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public void send(NotificationsRequest notificationsRequest) {
        notificationsRepository.save(
                Notification
                        .builder()
                        .toCustomerId(notificationsRequest.getToCustomerId())
                        .toCustomerEmail(notificationsRequest.getToCustomerName())
                        .sender("Amigoscode")
                        .message(notificationsRequest.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
