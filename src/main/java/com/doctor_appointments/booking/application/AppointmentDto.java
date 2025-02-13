package main.java.com.doctor_appointments.booking.application;

import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.shared.Appointment;

public record AppointmentDto(UUID appointmentId, UUID slotId, UUID patientId, String patientName, LocalDateTime reservedAt) {
    public static AppointmentDto fromRepo(AppointmentEntity appointmentEntity) {
        return new AppointmentDto(
                appointmentEntity.appointmentId(),
                appointmentEntity.slotId(),
                appointmentEntity.patientId(),
                appointmentEntity.patientName(),
                appointmentEntity.reservedAt());
    }

    public Appointment toSharedAppointment() {
        return new Appointment(appointmentId, slotId, patientId, patientName, reservedAt);
    }
}
