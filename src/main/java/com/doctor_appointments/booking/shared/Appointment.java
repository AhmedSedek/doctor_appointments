package main.java.com.doctor_appointments.booking.shared;

import java.time.LocalDateTime;
import java.util.UUID;

public record Appointment(UUID appointmentId, UUID slotId, UUID patientId, String patientName, LocalDateTime reservedAt) {}
