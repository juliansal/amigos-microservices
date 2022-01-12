package org.happykraken.notifications;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happykraken.clients.notifications.NotificationsRequest;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/notification")
public class NotificationsController {
    private final NotificationsService notificationsService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationsRequest notificationsRequest) {
        log.info("New notification... {}", notificationsRequest);
        notificationsService.send(notificationsRequest);
    }
}
