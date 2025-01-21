package main.java.com.doctor_appointments.booking.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotRequest;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotRequest;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;

public class AppointmentService implements IAppointmentService {

  private final IAppointmentRepo appointmentRepo;
  private final ReleaseSlotController releaseSlotController;
  private final ReserveSlotController reserveSlotController;

  AppointmentService(
      IAppointmentRepo appointmentRepo,
      ReleaseSlotController releaseSlotController,
      ReserveSlotController reserveSlotController) {
    this.appointmentRepo = appointmentRepo;
    this.releaseSlotController = releaseSlotController;
    this.reserveSlotController = reserveSlotController;
  }

  @Override
  public AppointmentDto bookAppointment(UUID slotId, UUID patientId, String patientName) {
    reserveSlotController.handle(new ReserveSlotRequest(slotId));
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
    releaseSlotController.handle(new ReleaseSlotRequest(deletedAppointment.slotId()));
  }

  @Override
  public void completeAppointment(UUID appointmentId) {
  }

  @Override
  public List<AppointmentDto> listAppointments() {
    return appointmentRepo
        .listAppointments()
        .stream()
        .map(appointmentEntity -> AppointmentDto.fromRepo(appointmentEntity))
        .toList();
  }

}
