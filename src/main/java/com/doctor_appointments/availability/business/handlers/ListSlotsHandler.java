package main.java.com.doctor_appointments.availability.business.handlers;

import main.java.com.doctor_appointments.availability.business.SlotDto;
import main.java.com.doctor_appointments.availability.data.ISlotRepo;

import java.util.List;

public class ListSlotsHandler {
    private final ISlotRepo slotRepo;

    ListSlotsHandler(ISlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }
    public List<SlotDto> handle(boolean availableOnly) {
        var slotsStream = slotRepo.listSlots().stream();
        if (availableOnly) {
            slotsStream = slotsStream.filter(slotEntity -> !slotEntity.isReserved());
        }
        return slotsStream.map(slotEntity -> SlotDto.fromRepo(slotEntity)).toList();
    }
}
