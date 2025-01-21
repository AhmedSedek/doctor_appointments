package main.java.com.doctor_appointments.availability.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.model.SlotEntity;

public class DoctorAvailabilityService implements IDoctorAvailabilityService {

  private final ISlotRepo slotRepo;

  DoctorAvailabilityService(ISlotRepo slotRepo) {
    this.slotRepo = slotRepo;
  }

  @Override
  public SlotDto addSlot(UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {
    UUID slotId = UUID.randomUUID();
    SlotEntity slotEntity = new SlotEntity(slotId, doctorId, doctorName, time, isReserved, cost);
    // TODO - Handle conflicting slotIds in the database.
    slotRepo.addSlot(slotEntity);
    return SlotDto.fromRepo(slotEntity);
  }

  @Override
  public List<SlotDto> listSlots(boolean availableOnly) {
    var slotsStream = slotRepo.listSlots().stream();
    if (availableOnly) {
      slotsStream = slotsStream.filter(slotEntity -> !slotEntity.isReserved());
    }
    return slotsStream.map(slotEntity -> SlotDto.fromRepo(slotEntity)).toList();
  }

  @Override
  public void releaseSlot(UUID slotId) {
    slotRepo.releaseSlot(slotId);
  }

  @Override
  public void reserveSlot(UUID slotId) {
    slotRepo.reserveSlot(slotId);
  }
}
