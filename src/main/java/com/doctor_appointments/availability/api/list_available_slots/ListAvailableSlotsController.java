package main.java.com.doctor_appointments.availability.api.list_available_slots;

import main.java.com.doctor_appointments.availability.business.ListSlotsHandler;

public class ListAvailableSlotsController {
    private final ListSlotsHandler listSlotsHandler;

    ListAvailableSlotsController(ListSlotsHandler listSlotsHandler) {
        this.listSlotsHandler = listSlotsHandler;
    }
    public ListAvailableSlotsResponse handle(ListAvailableSlotsRequest request) {
        var slots = this.listSlotsHandler.handleAvailableOnly();
        return new ListAvailableSlotsResponse(slots);
    }
}
