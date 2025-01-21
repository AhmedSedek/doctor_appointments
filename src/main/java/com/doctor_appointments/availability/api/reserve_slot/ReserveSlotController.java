package main.java.com.doctor_appointments.availability.api.reserve_slot;

import main.java.com.doctor_appointments.availability.business.handlers.ReserveSlotHandler;

public class ReserveSlotController {

  private final ReserveSlotHandler reserveSlotHandler;

  ReserveSlotController(ReserveSlotHandler reserveSlotHandler) {
    this.reserveSlotHandler = reserveSlotHandler;
  }

  public ReserveSlotResponse handle(ReserveSlotRequest request) {
    reserveSlotHandler.handle(request.slotId());
    return new ReserveSlotResponse();
  }
}
