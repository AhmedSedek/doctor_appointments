package main.java.com.doctor_appointments.availability.api.list_slots;

import main.java.com.doctor_appointments.availability.business.ListSlotsHandler;

public class ListSlotsController {

    private final ListSlotsHandler listSlotsHandler;

    ListSlotsController(ListSlotsHandler listSlotsHandler) {
        this.listSlotsHandler = listSlotsHandler;
    }
    public ListSlotsResponse handle(ListSlotsRequest request) {
        var slots = this.listSlotsHandler.handle();
        return new ListSlotsResponse(slots);
    }
}
