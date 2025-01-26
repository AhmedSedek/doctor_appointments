package main.java.com.doctor_appointments.booking.shared.exceptions;

import java.util.UUID;

public class AppointmentAlreadyCompletedException extends Exception {
  public AppointmentAlreadyCompletedException(UUID appointmentId) {
    super(String.format("Appointment %s already completed", appointmentId));
  }

}
