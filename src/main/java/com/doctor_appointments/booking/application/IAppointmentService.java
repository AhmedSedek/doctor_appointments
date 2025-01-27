package main.java.com.doctor_appointments.booking.application;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.shared.Slot;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentAlreadyExistsException;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotNotFoundException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotAlreadyBookedException;

public interface IAppointmentService {
  AppointmentDto bookAppointment(UUID slotId, UUID patentId, String patientName) throws AppointmentAlreadyExistsException, SlotAlreadyBookedException, SlotNotFoundException;

  void cancelAppointment(UUID appointmentId) throws AppointmentNotFoundException;

  void completeAppointment(UUID appointmentId) throws AppointmentNotFoundException;

  List<AppointmentDto> listAppointments();

  List<Slot> listAvailableSlots();
}
