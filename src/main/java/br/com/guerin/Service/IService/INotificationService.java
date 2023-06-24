package br.com.guerin.Service.IService;

import java.util.List;
import DTO.Notification.*;

public interface INotificationService {
    void addNotification(Notification notification);
    List<Notification> getNotifications();
    boolean hasNotifications();
}