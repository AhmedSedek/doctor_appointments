package main.java.com.doctor_appointments.management.shell.api.complete_appointment;

import main.java.com.doctor_appointments.management.core.services.IManagementService;

public class CompleteAppointmentController {

  private final IManagementService managementService;
  CompleteAppointmentController(IManagementService managementService) {
    this.managementService = managementService;
  }
  public CompleteAppointmentResponse handle(CompleteAppointmentRequest request) {
    managementService.completeAppointment(request.appointmentId());
    return new CompleteAppointmentResponse();
  }
}
