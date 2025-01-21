package main.java.com.doctor_appointments.availability.api.reserve_slot;

import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;

public class ReserveSlotController {

  private final IDoctorAvailabilityService doctorAvailabilityService;

  ReserveSlotController(IDoctorAvailabilityService slotService) {
    this.doctorAvailabilityService = slotService;
  }

  public ReserveSlotResponse handle(ReserveSlotRequest request) {
    doctorAvailabilityService.reserveSlot(request.slotId());
    return new ReserveSlotResponse();
  }
}
