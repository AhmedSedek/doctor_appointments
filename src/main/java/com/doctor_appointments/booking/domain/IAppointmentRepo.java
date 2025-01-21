package main.java.com.doctor_appointments.booking.domain;

import java.util.List;
import java.util.UUID;

public interface IAppointmentRepo {

    void complete(UUID appointmentId);

    AppointmentEntity delete(UUID appointmentId);

    List<AppointmentEntity> listAppointments();

    void save(AppointmentEntity appointment);
}
