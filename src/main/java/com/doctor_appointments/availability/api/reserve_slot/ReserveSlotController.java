package main.java.com.doctor_appointments.availability.api.reserve_slot;

import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;

public class ReserveSlotController {

  private final IDoctorAvailabilityService doctorAvailabilityService;

  ReserveSlotController(IDoctorAvailabilityService slotService) {
    this.doctorAvailabilityService = slotService;
  }

  public ReserveSlotResponse handle(ReserveSlotRequest request) throws SlotAlreadyReservedException, SlotNotFoundException {
    doctorAvailabilityService.reserveSlot(request.slotId());
    return new ReserveSlotResponse();
  }
}
