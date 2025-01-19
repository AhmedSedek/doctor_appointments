package main.java.com.doctor_appointments.booking.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentEntity(UUID appointmentId, UUID slotId, UUID patientId, String patientName, LocalDateTime reservedAt) {

}
