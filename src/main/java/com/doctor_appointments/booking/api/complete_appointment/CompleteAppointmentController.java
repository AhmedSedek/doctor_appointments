package main.java.com.doctor_appointments.booking.api.complete_appointment;

import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentAlreadyCompletedException;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;

public class CompleteAppointmentController {
  private final IAppointmentService appointmentService;

  CompleteAppointmentController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }
  public CompleteAppointmentResponse handle(CompleteAppointmentRequest request) throws AppointmentAlreadyCompletedException, AppointmentNotFoundException {
    appointmentService.completeAppointment(request.appointmentId());
    return new CompleteAppointmentResponse();
  }
}
