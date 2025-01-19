package main.java.com.doctor_appointments.booking.api.complete_appointment;

import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentResponse;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;

public class CompleteAppointmentController {
  private final IAppointmentService appointmentService;

  CompleteAppointmentController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }
  public CompleteAppointmentResponse handle(CompleteAppointmentRequest request) {
    appointmentService.completeAppointment(request.appointmentId());
    return new CompleteAppointmentResponse();
  }
}
