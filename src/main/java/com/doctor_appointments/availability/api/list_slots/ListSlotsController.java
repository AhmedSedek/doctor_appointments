package main.java.com.doctor_appointments.availability.api.list_slots;

import java.util.List;
import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.service.SlotDto;
import main.java.com.doctor_appointments.availability.shared.Slot;

public class ListSlotsController {

    private final IDoctorAvailabilityService doctorAvailabilityService;

    ListSlotsController(IDoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }
    public ListSlotsResponse handle(ListSlotsRequest request) {
        List<SlotDto> slots = this.doctorAvailabilityService.listSlots(request.availableOnly());
        List<Slot> sharedSlots = slots.stream().map(slotDto -> slotDto.toSharedSlot()).toList();
        return new ListSlotsResponse(sharedSlots);
    }
}
