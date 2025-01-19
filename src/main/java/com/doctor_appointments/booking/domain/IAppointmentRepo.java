package main.java.com.doctor_appointments.booking.domain;

import java.util.UUID;

public interface IAppointmentRepo {

    void complete(UUID appointmentId);

    AppointmentEntity delete(UUID appointmentId);

    void save(AppointmentEntity appointment);
}
