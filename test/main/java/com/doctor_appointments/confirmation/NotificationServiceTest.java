package main.java.com.doctor_appointments.confirmation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class NotificationServiceTest {
  private Logger mockLogger;
  private NotificationService notificationService;

  @BeforeEach
  void setUp() {
    mockLogger = mock(Logger.class);
    notificationService = new NotificationService(mockLogger);
  }

  @Test
  void testNotify_logsObjectAndRecipient() {
    String to = "recipient@gmail.com";
    String message = "Testing message";
    UUID messageId = UUID.randomUUID();
    LogMessage logMessage = new LogMessage(messageId, message);

    notificationService.notify(to, logMessage);

    String loggedMessage = getLoggedMessage();
    assertTrue(loggedMessage.contains(to));
    assertTrue(loggedMessage.contains(message));
    assertTrue(loggedMessage.contains(logMessage.toString()));

  }

  private String getLoggedMessage() {
    ArgumentCaptor<String> logRecordCaptor = ArgumentCaptor.forClass(String.class);
    verify(mockLogger).info(logRecordCaptor.capture());
    return logRecordCaptor.getValue();
  }

  private record LogMessage(UUID messageId, String message){}
}