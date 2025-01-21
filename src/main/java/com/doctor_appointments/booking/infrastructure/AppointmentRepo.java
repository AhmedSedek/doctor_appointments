package main.java.com.doctor_appointments.booking.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.confirmation.INotificationService;

public class AppointmentRepo implements IAppointmentRepo {
    // This is hardcoded as it only works for simplicity. Ideally it should be a config somewhere
    // (even if it's for one doctor) or generally fetched from some Doctor repo using doctor ID.
    private static final String DOCTOR_EMAIL = "doctor@email.com";

    private final INotificationService notificationService;

    private final Map<UUID, AppointmentEntity> appointments;
    private final Map<UUID, AppointmentEntity> completedAppointments;

    AppointmentRepo(INotificationService notificationService){
        this.appointments = new HashMap<>();
        this.completedAppointments = new HashMap<>();
        this.notificationService = notificationService;
    }

    @Override
    public synchronized void complete(UUID appointmentId) {
        if (completedAppointments.containsKey(completedAppointments)) {
            throw new IllegalArgumentException(String.format("Appointment %s already completed", appointmentId));
        }
        var appointment = appointments.remove(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException(String.format("Appointment %s does not exist", appointmentId));
        }
        completedAppointments.put(appointmentId, appointment);
        System.out.println(String.format("Appointment %d completed", appointmentId));
    }

    @Override
    public synchronized AppointmentEntity delete(UUID appointmentId) {
        var removedAppointment = appointments.remove(appointmentId);
        if (removedAppointment == null) {
            throw new IllegalArgumentException(String.format("Appointment %s does not exist", appointmentId));
        }
        return removedAppointment;
    }

    @Override
    public List<AppointmentEntity> listAppointments() {
        return appointments.values().stream().toList();
    }

    @Override
    public synchronized void save(AppointmentEntity appointment) {
        if (appointments.containsKey(appointment.appointmentId())) {
            throw new IllegalArgumentException("Conflict appointment ID: " + appointment.appointmentId());
        }
        appointments.put(appointment.appointmentId(), appointment);
        sendNotifications(appointment);
    }

    private void sendNotifications(AppointmentEntity appointment) {
        var patientEmail = patientEmail(appointment.patientId());
        notificationService.notify(DOCTOR_EMAIL, appointment.toString());
        notificationService.notify(patientEmail, appointment.toString());
    }

    private String patientEmail(UUID patientId) {
        return String.format("%s@email.com", patientId.toString());
    }
}
