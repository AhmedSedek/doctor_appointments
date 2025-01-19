package main.java.com.doctor_appointments.booking.application;

import java.util.UUID;

public interface IAppointmentService {
  AppointmentDto bookAppointment(UUID slotId, UUID patentId, String patientName);
}
