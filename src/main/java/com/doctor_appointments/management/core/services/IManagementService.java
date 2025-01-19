package main.java.com.doctor_appointments.management.core.services;

import java.util.UUID;

public interface IManagementService {
    void cancelAppointment(UUID appointmentId);

    void completeAppointment(UUID appointmentId);
}
