package main.java.com.doctor_appointments.booking.api.list_appointments;

import java.util.List;
import main.java.com.doctor_appointments.booking.application.AppointmentDto;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.shared.Appointment;

public class ListAppointmentsController {
  private final IAppointmentService appointmentService;

  ListAppointmentsController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  public ListAppointmentsResponse handle(ListAppointmentsRequest request) {
    // TODO - Consider filtering out the appointments with dates in the past.
    List<AppointmentDto> appointments = appointmentService.listAppointments();
    List<Appointment> mappedAppointments = appointments
        .stream()
        .map(appointmentDto -> appointmentDto.toSharedAppointment())
        .toList();

    return new ListAppointmentsResponse(mappedAppointments);
  }
}
