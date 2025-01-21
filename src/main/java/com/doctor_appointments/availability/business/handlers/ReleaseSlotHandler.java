package main.java.com.doctor_appointments.availability.business.handlers;

import java.util.UUID;
import main.java.com.doctor_appointments.availability.data.ISlotRepo;

public class ReleaseSlotHandler {
  private final ISlotRepo slotRepo;

  ReleaseSlotHandler(ISlotRepo slotRepo) {
    this.slotRepo = slotRepo;
  }
  public void handle(UUID slotId) {
    slotRepo.releaseSlot(slotId);
  }
}
