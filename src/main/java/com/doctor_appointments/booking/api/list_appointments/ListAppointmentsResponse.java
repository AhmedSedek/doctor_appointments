package main.java.com.doctor_appointments.booking.api.list_appointments;

import java.util.List;
import main.java.com.doctor_appointments.booking.shared.Appointment;

public record ListAppointmentsResponse(List<Appointment> appointments) {}
