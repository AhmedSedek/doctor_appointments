package main.java.com.doctor_appointments.availability.business.handlers;

import main.java.com.doctor_appointments.availability.business.SlotDto;
import main.java.com.doctor_appointments.availability.shared.ISlotRepo;
import main.java.com.doctor_appointments.availability.data.SlotEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddSlotHandler {
    private final ISlotRepo slotRepo;

    AddSlotHandler(ISlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }
    public SlotDto handle(UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {
        UUID slotId = UUID.randomUUID();
        SlotEntity slotEntity = new SlotEntity(slotId, doctorId, doctorName, time, isReserved, cost);
        // TODO - Handle conflicting slotIds in the database.
        slotRepo.addSlot(slotEntity);
        return SlotDto.fromRepo(slotEntity);
    }
}
