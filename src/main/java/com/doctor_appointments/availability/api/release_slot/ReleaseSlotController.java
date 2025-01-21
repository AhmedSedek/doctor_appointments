package main.java.com.doctor_appointments.availability.api.release_slot;

import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;

public class ReleaseSlotController {

  private final IDoctorAvailabilityService doctorAvailabilityService;

  ReleaseSlotController(IDoctorAvailabilityService doctorAvailabilityService) {
    this.doctorAvailabilityService = doctorAvailabilityService;
  }

  public ReleaseSlotResponse handle(ReleaseSlotRequest request) {
    doctorAvailabilityService.releaseSlot(request.slotId());
    return new ReleaseSlotResponse();
  }
}
