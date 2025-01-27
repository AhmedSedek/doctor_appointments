package main.java.com.doctor_appointments.booking.domain;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentAlreadyExistsException;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;

public interface IAppointmentRepo {

    void add(AppointmentEntity appointment) throws AppointmentAlreadyExistsException;

    void complete(UUID appointmentId) throws AppointmentNotFoundException;

    AppointmentEntity delete(UUID appointmentId) throws AppointmentNotFoundException;

    List<AppointmentEntity> listAppointments();
}
