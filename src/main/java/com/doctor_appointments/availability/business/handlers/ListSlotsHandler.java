package main.java.com.doctor_appointments.availability.business.handlers;

import main.java.com.doctor_appointments.availability.business.SlotDto;
import main.java.com.doctor_appointments.availability.shared.ISlotRepo;

import java.util.List;

public class ListSlotsHandler {
    private final ISlotRepo slotRepo;

    ListSlotsHandler(ISlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }
    public List<SlotDto> handle() {
        return slotRepo.listSlots().stream().map(slotEntity -> SlotDto.fromRepo(slotEntity)).toList();
    }

    public List<SlotDto> handleAvailableOnly() {
        return slotRepo
                .listSlots()
                .stream()
                .filter(slotEntity -> !slotEntity.isReserved())
                .map(slotEntity -> SlotDto.fromRepo(slotEntity))
                .toList();
    }
}
