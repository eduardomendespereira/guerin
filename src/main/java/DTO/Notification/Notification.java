package DTO.Notification;

public class Notification {    
    private String message;
    private NotificationType type;

    public Notification(String message, NotificationType type) {        
        this.message = message;
        this.type = type;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }            
}