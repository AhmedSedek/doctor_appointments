package main.java.com.doctor_appointments.management.core.exceptions;

public class AppointmentNotFoundException extends Exception {
  public AppointmentNotFoundException(String message) {
    super(message);
  }
}
