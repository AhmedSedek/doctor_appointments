package main.java.com.doctor_appointments.availability.api.list_slots;

import main.java.com.doctor_appointments.availability.business.handlers.ListSlotsHandler;

public class ListSlotsController {

    private final ListSlotsHandler listSlotsHandler;

    ListSlotsController(ListSlotsHandler listSlotsHandler) {
        this.listSlotsHandler = listSlotsHandler;
    }
    public ListSlotsResponse handle(ListSlotsRequest request) {
        var slots = this.listSlotsHandler.handle(request.availableOnly());
        return new ListSlotsResponse(slots);
    }
}
