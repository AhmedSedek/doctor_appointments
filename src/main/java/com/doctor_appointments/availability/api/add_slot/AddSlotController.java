package main.java.com.doctor_appointments.availability.api.add_slot;

import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.service.SlotDto;

public class AddSlotController {

    private final IDoctorAvailabilityService doctorAvailabilityService;

    AddSlotController(IDoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }
    public AddSlotResponse handle(AddSlotRequest request) {
        SlotDto storedSlot = doctorAvailabilityService.addSlot(request.doctorId(), request.doctorName(), request.time(), request.isReserved(), request.cost());
        return new AddSlotResponse(storedSlot.slotId());
    }
}
