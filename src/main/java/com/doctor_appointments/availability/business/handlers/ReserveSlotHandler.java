package main.java.com.doctor_appointments.availability.business.handlers;

import java.util.UUID;
import main.java.com.doctor_appointments.availability.data.ISlotRepo;

public class ReserveSlotHandler {
  private final ISlotRepo slotRepo;

  ReserveSlotHandler(ISlotRepo slotRepo) {
    this.slotRepo = slotRepo;
  }
  public void handle(UUID slotId) {
    slotRepo.reserveSlot(slotId);
  }
}
