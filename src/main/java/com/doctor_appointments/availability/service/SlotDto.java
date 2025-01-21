package main.java.com.doctor_appointments.availability.service;

import main.java.com.doctor_appointments.availability.model.SlotEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.shared.Slot;

public record SlotDto(UUID slotId, UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {
    public static SlotDto fromRepo(SlotEntity slotEntity) {
        return new SlotDto(slotEntity.slotId(),
                slotEntity.doctorId(),
                slotEntity.doctorName(),
                slotEntity.time(),
                slotEntity.isReserved(),
                slotEntity.cost());
    }

    public Slot toSharedSlot() {
        return new Slot(slotId, doctorId, doctorName, time, isReserved, cost);
    }
}
