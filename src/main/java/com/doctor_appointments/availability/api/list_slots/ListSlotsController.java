package main.java.com.doctor_appointments.availability.api.list_slots;

import java.util.List;
import main.java.com.doctor_appointments.availability.service.IDoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.service.SlotDto;

public class ListSlotsController {

    private final IDoctorAvailabilityService doctorAvailabilityService;

    ListSlotsController(IDoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }
    public ListSlotsResponse handle(ListSlotsRequest request) {
        List<SlotDto> slots = this.doctorAvailabilityService.listSlots(request.availableOnly());
        return new ListSlotsResponse(slots);
    }
}
