package org.happykraken.notifications.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happykraken.clients.notifications.NotificationsRequest;
import org.happykraken.notifications.NotificationsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationsService notificationsService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(NotificationsRequest notificationsRequest) {
        log.info("Consumed {} from queue", notificationsRequest);
        notificationsService.send(notificationsRequest);
    }
}
