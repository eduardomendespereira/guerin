package br.com.guerin.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import DTO.Notification.*;
import br.com.guerin.Service.IService.INotificationService;

@Service
public class NotificationService implements INotificationService {
    private List<Notification> notifications;

    public NotificationService() {
        this.notifications = new ArrayList<Notification>();
    }

    public void addNotification(Notification notification) {        
        this.notifications.add(notification);        
    }

    public List<Notification> getNotifications()  {
        return this.notifications;
    }   

    public boolean hasNotifications() {
        return !this.notifications.isEmpty();
    }
}