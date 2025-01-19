package main.java.com.doctor_appointments.booking.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;

public class AppointmentRepo implements IAppointmentRepo {
    private final Map<UUID, AppointmentEntity> appointments;

    AppointmentRepo(){
        this.appointments = new HashMap<>();
    }

    @Override
    public void save(AppointmentEntity appointment) {
        if (appointments.containsKey(appointment.appointmentId())) {
            throw new IllegalArgumentException("Conflict appointment ID: " + appointment.appointmentId());
        }
        appointments.put(appointment.appointmentId(), appointment);
    }
}
