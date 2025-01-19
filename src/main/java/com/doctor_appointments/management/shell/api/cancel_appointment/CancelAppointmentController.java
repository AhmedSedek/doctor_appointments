package main.java.com.doctor_appointments.management.shell.api.cancel_appointment;

import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentResponse;
import main.java.com.doctor_appointments.management.core.services.IManagementService;

public class CancelAppointmentController {
  private final IManagementService managementService;

  CancelAppointmentController(IManagementService managementService) {
    this.managementService = managementService;
  }
  public CancelAppointmentResponse handle(CancelAppointmentRequest request) {
    managementService.cancelAppointment(request.appointmentId());
    return new CancelAppointmentResponse();
  }
}
