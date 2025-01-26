package main.java.com.doctor_appointments.booking.shared.exceptions;

import java.util.UUID;

public class AppointmentNotFoundException extends Exception {
  public AppointmentNotFoundException(UUID appointmentId) {
    super(String.format("Appointment %s does not exist", appointmentId));
  }
}
