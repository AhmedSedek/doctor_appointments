package main.java.com.doctor_appointments.management.core.services;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import main.java.com.doctor_appointments.management.core.exceptions.AppointmentNotFoundException;

public interface IManagementService {
    void cancelAppointment(UUID appointmentId);

    void completeAppointment(UUID appointmentId) throws AppointmentNotFoundException;

    List<Appointment> listAppointments();
}
