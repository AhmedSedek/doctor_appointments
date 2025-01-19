package main.java.com.doctor_appointments.booking.api.cancel_appointment;

import java.util.UUID;

public record CancelAppointmentRequest(UUID appointmentId) {

}
