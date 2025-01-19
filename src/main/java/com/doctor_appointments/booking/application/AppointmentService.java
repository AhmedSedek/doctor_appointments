package main.java.com.doctor_appointments.booking.application;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.shared.ISlotRepo;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;

public class AppointmentService implements IAppointmentService {

  private final IAppointmentRepo appointmentRepo;
  private final ISlotRepo slotRepo;

  AppointmentService(IAppointmentRepo appointmentRepo, ISlotRepo slotRepo) {
    this.appointmentRepo = appointmentRepo;
    this.slotRepo = slotRepo;
  }

  @Override
  public AppointmentDto bookAppointment(UUID slotId, UUID patientId, String patientName) {
    slotRepo.reserveSlot(slotId);
    UUID appointmentId = UUID.randomUUID();
    AppointmentEntity appointment = new AppointmentEntity(appointmentId, slotId, patientId, patientName,
        LocalDateTime.now());
    // TODO - Handle conflicting appointmentIds in the database.
    appointmentRepo.save(appointment);
    return AppointmentDto.fromRepo(appointment);
  }

  @Override
  public void cancelAppointment(UUID appointmentId) {
    AppointmentEntity deletedAppointment = appointmentRepo.delete(appointmentId);
    slotRepo.releaseSlot(deletedAppointment.slotId());
  }

}
