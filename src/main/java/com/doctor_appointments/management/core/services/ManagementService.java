package main.java.com.doctor_appointments.management.core.services;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentController;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.list_appointments.ListAppointmentsController;
import main.java.com.doctor_appointments.booking.api.list_appointments.ListAppointmentsRequest;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import main.java.com.doctor_appointments.management.core.exceptions.AppointmentNotFoundException;

public class ManagementService implements IManagementService {

  private final CancelAppointmentController cancelAppointmentController;
  private final CompleteAppointmentController completeAppointmentController;
  private final ListAppointmentsController listAppointmentsController;

  ManagementService(
      CancelAppointmentController cancelAppointmentController,
      CompleteAppointmentController completeAppointmentController,
      ListAppointmentsController listAppointmentsController){
    this.cancelAppointmentController = cancelAppointmentController;
    this.completeAppointmentController = completeAppointmentController;
    this.listAppointmentsController = listAppointmentsController;
  }
  @Override
  public void cancelAppointment(UUID appointmentId) {
    var request = new CancelAppointmentRequest(appointmentId);
    try {
      cancelAppointmentController.handle(request);
    } catch (Exception e) {
      // Ignore errors
    }
  }

  @Override
  public void completeAppointment(UUID appointmentId) throws AppointmentNotFoundException {
    var request = new CompleteAppointmentRequest(appointmentId);
    try {
      completeAppointmentController.handle(request);
    } catch (main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException e) {
      throw new AppointmentNotFoundException(e.getMessage());
    }
  }

  @Override
  public List<Appointment> listAppointments() {
    var request = new ListAppointmentsRequest();
    return listAppointmentsController.handle(request).appointments();
  }
}
