package main.java.com.doctor_appointments.management.core.services;

import java.util.UUID;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentController;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentRequest;

public class ManagementService implements IManagementService {

  private final CancelAppointmentController cancelAppointmentController;
  private final CompleteAppointmentController completeAppointmentController;

  ManagementService(
      CancelAppointmentController cancelAppointmentController,
      CompleteAppointmentController completeAppointmentController){
    this.cancelAppointmentController = cancelAppointmentController;
    this.completeAppointmentController = completeAppointmentController;
  }
  @Override
  public void cancelAppointment(UUID appointmentId) {
    var request = new CancelAppointmentRequest(appointmentId);
    cancelAppointmentController.handle(request);
  }

  @Override
  public void completeAppointment(UUID appointmentId) {
    var request = new CompleteAppointmentRequest(appointmentId);
    completeAppointmentController.handle(request);
  }
}
