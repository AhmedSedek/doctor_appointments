package main.java.com.doctor_appointments.availability.api.add_slot;

import main.java.com.doctor_appointments.availability.business.AddSlotHandler;
import main.java.com.doctor_appointments.availability.business.SlotDto;

public class AddSlotController {

    private final AddSlotHandler addSlotHandler;

    AddSlotController(AddSlotHandler addSlotHandler) {
        this.addSlotHandler = addSlotHandler;
    }
    public AddSlotResponse handle(AddSlotRequest request) {
        SlotDto storedSlot = addSlotHandler.handle(request.doctorId(), request.doctorName(), request.time(), request.isReserved(), request.cost());
        return new AddSlotResponse(storedSlot.slotId());
    }
}
