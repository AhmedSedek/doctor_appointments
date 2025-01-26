package main.java.com.doctor_appointments.booking.shared.exceptions;

import java.util.UUID;

public class AppointmentAlreadyExistsException extends Exception {
  public AppointmentAlreadyExistsException(UUID appointmentId) {
    super(String.format("Appointment %s already exists", appointmentId));
  }

}
