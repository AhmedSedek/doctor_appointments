package main.java.com.doctor_appointments.booking.api.list_available_slots;

import java.util.List;
import main.java.com.doctor_appointments.availability.shared.Slot;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;

public class ListAvailableSlotsController {
  private final IAppointmentService appointmentService;

  ListAvailableSlotsController(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  public ListAvailableSlotsResponse handle(ListAvailableSlotsRequest request) {
    List<Slot> slots = appointmentService.listAvailableSlots();
    return new ListAvailableSlotsResponse(slots);
  }
}
