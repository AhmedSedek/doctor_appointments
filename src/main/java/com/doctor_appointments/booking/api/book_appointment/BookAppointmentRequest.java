package main.java.com.doctor_appointments.booking.api.book_appointment;

import java.util.UUID;

public record BookAppointmentRequest(UUID slotId, UUID patientId, String patientName) {}
