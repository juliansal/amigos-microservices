package org.happykraken.clients.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notifications")
public interface NotificationsClient {

    @PostMapping("api/v1/notification")
    void sendNotification(NotificationsRequest notificationsRequest);
}
