package main.java.com.doctor_appointments.booking.api.book_appointment;

import main.java.com.doctor_appointments.booking.application.AppointmentDto;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotAlreadyBookedException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotNotFoundException;

public class BookAppointmentController {

  private final IAppointmentService appointmentService;

  BookAppointmentController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }
  public BookAppointmentResponse handle(BookAppointmentRequest request) throws SlotAlreadyBookedException, SlotNotFoundException {
    AppointmentDto storedAppointment = appointmentService.bookAppointment(request.slotId(), request.patientId(), request.patientName());
    return new BookAppointmentResponse(storedAppointment.appointmentId());
  }
}
