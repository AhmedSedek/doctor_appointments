package main.java.com.doctor_appointments.availability.api.release_slot;

import main.java.com.doctor_appointments.availability.business.handlers.ReleaseSlotHandler;

public class ReleaseSlotController {

  private final ReleaseSlotHandler releaseSlotHandler;

  ReleaseSlotController(ReleaseSlotHandler releaseSlotHandler) {
    this.releaseSlotHandler = releaseSlotHandler;
  }

  public ReleaseSlotResponse handle(ReleaseSlotRequest request) {
    releaseSlotHandler.handle(request.slotId());
    return new ReleaseSlotResponse();
  }
}
