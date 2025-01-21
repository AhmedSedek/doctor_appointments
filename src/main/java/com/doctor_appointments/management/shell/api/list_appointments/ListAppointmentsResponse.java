package main.java.com.doctor_appointments.management.shell.api.list_appointments;

import java.util.List;
import main.java.com.doctor_appointments.booking.application.AppointmentDto;

public record ListAppointmentsResponse(List<AppointmentDto> appointments) {

}
