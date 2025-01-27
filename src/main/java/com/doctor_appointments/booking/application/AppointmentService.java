package main.java.com.doctor_appointments.booking.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsRequest;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotRequest;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotRequest;
import main.java.com.doctor_appointments.availability.shared.Slot;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentAlreadyExistsException;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotAlreadyBookedException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotNotFoundException;

public class AppointmentService implements IAppointmentService {

  private final IAppointmentRepo appointmentRepo;
  private final ListSlotsController listSlotsController;
  private final ReleaseSlotController releaseSlotController;
  private final ReserveSlotController reserveSlotController;

  AppointmentService(
      IAppointmentRepo appointmentRepo,
      ListSlotsController listSlotsController,
      ReleaseSlotController releaseSlotController,
      ReserveSlotController reserveSlotController) {
    this.appointmentRepo = appointmentRepo;
    this.listSlotsController = listSlotsController;
    this.releaseSlotController = releaseSlotController;
    this.reserveSlotController = reserveSlotController;
  }

  @Override
  public AppointmentDto bookAppointment(UUID slotId, UUID patientId, String patientName) throws AppointmentAlreadyExistsException, SlotAlreadyBookedException, SlotNotFoundException {
    reserveSlot(slotId);
    UUID appointmentId = UUID.randomUUID();
    AppointmentEntity appointment = new AppointmentEntity(appointmentId, slotId, patientId, patientName,
        LocalDateTime.now());
    // TODO - Handle conflicting appointmentIds in the database.
    appointmentRepo.add(appointment);
    return AppointmentDto.fromRepo(appointment);
  }

  @Override
  public void cancelAppointment(UUID appointmentId) throws AppointmentNotFoundException {
    AppointmentEntity deletedAppointment = appointmentRepo.delete(appointmentId);
    releaseSlot(deletedAppointment.slotId());
  }

  @Override
  public void completeAppointment(UUID appointmentId) throws AppointmentNotFoundException {
    appointmentRepo.complete(appointmentId);
  }

  @Override
  public List<AppointmentDto> listAppointments() {
    return appointmentRepo
        .listAppointments()
        .stream()
        .map(appointmentEntity -> AppointmentDto.fromRepo(appointmentEntity))
        .toList();
  }

  @Override
  public List<Slot> listAvailableSlots() {
    return listSlotsController.handle(new ListSlotsRequest(/*availableOnly=*/true)).slots();
  }

  private void releaseSlot(UUID slotId) {
    try {
      releaseSlotController.handle(new ReleaseSlotRequest(slotId));
    } catch (Exception e) {
      // Ignore errors
    }
  }

  private void reserveSlot(UUID slotId) throws SlotAlreadyBookedException, SlotNotFoundException {
    try {
      reserveSlotController.handle(new ReserveSlotRequest(slotId));
    } catch (SlotAlreadyReservedException e) {
      throw new SlotAlreadyBookedException(e.getMessage());
    } catch (main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException e) {
      throw new SlotNotFoundException(e.getMessage());
    }
  }
}
