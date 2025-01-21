package main.java.com.doctor_appointments.management.core.services;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.application.AppointmentDto;

public interface IManagementService {
    void cancelAppointment(UUID appointmentId);

    void completeAppointment(UUID appointmentId);

    List<AppointmentDto> listAppointments();
}
