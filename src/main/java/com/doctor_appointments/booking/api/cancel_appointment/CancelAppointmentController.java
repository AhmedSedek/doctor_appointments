package main.java.com.doctor_appointments.booking.api.cancel_appointment;

import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;

public class CancelAppointmentController {
  private final IAppointmentService appointmentService;

  CancelAppointmentController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }
  public CancelAppointmentResponse handle(CancelAppointmentRequest request) throws AppointmentNotFoundException {
    appointmentService.cancelAppointment(request.appointmentId());
    return new CancelAppointmentResponse();
  }
}
