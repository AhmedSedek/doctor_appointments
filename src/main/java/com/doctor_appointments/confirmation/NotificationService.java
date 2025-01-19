package main.java.com.doctor_appointments.confirmation;

public class NotificationService implements INotificationService {

  @Override
  public void notify(String to, Object message) {
    System.out.println(String.format("Sending %s to %s", message, to));
  }
}
