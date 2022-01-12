package org.happykraken.notifications;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notification, Integer> {
}
