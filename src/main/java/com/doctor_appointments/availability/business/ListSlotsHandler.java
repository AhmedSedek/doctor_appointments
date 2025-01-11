package main.java.com.doctor_appointments.availability.business;

import main.java.com.doctor_appointments.availability.data.ISlotRepo;

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
