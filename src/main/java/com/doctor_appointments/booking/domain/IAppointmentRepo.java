package main.java.com.doctor_appointments.booking.domain;

import java.util.UUID;

public interface IAppointmentRepo {

    AppointmentEntity delete(UUID appointmentId);

    void save(AppointmentEntity appointment);
}
