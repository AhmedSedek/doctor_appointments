package main.java.com.doctor_appointments.availability.api.list_available_slots;

import java.util.List;
import java.util.stream.Collectors;
import main.java.com.doctor_appointments.availability.business.handlers.ListSlotsHandler;
import main.java.com.doctor_appointments.availability.business.SlotDto;
import main.java.com.doctor_appointments.availability.shared.Slot;

public class ListAvailableSlotsController {
    private final ListSlotsHandler listSlotsHandler;

    ListAvailableSlotsController(ListSlotsHandler listSlotsHandler) {
        this.listSlotsHandler = listSlotsHandler;
    }
    public ListAvailableSlotsResponse handle(ListAvailableSlotsRequest request) {
        List<SlotDto> slots = this.listSlotsHandler.handleAvailableOnly();
        List<Slot> mappedSlots = slots
            .stream()
            .map(slotDto -> slotDto.toSharedSlot())
            .collect(Collectors.toList());

        return new ListAvailableSlotsResponse(mappedSlots);
    }
}
