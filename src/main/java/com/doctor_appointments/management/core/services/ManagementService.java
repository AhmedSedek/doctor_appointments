package main.java.com.doctor_appointments.management.core.services;

import java.util.UUID;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;

public class ManagementService implements IManagementService {

  private final CancelAppointmentController cancelAppointmentController;
  ManagementService(CancelAppointmentController cancelAppointmentController){
    this.cancelAppointmentController = cancelAppointmentController;
  }
  @Override
  public void cancelAppointment(UUID appointmentId) {
    var request = new CancelAppointmentRequest(appointmentId);
    cancelAppointmentController.handle(request);
  }
}
