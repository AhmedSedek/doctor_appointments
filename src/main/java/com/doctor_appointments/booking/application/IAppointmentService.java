package main.java.com.doctor_appointments.booking.application;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.shared.Slot;

public interface IAppointmentService {
  AppointmentDto bookAppointment(UUID slotId, UUID patentId, String patientName);

  void cancelAppointment(UUID appointmentId);

  void completeAppointment(UUID appointmentId);

  List<AppointmentDto> listAppointments();

  List<Slot> listAvailableSlots();
}
