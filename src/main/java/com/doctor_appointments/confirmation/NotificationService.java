package main.java.com.doctor_appointments.confirmation;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationService implements INotificationService {

  private final Logger logger;

  NotificationService(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void notify(String to, Object message) {
    logger.info(String.format("Sending %s to %s", message, to));
  }
}
