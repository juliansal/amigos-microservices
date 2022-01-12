package org.happykraken.clients.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationsRequest {
    private Integer toCustomerId;
    private String toCustomerName;
    private String message;

}
