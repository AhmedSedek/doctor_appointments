package main.java.com.doctor_appointments.booking.api.cancel_appointment;

import main.java.com.doctor_appointments.booking.application.IAppointmentService;

public class CancelAppointmentController {
  private final IAppointmentService appointmentService;

  CancelAppointmentController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }
  public CancelAppointmentResponse handle(CancelAppointmentRequest request) {
    appointmentService.cancelAppointment(request.appointmentId());
    return new CancelAppointmentResponse();
  }
}
