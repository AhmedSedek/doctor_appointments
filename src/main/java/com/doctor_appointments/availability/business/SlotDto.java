package main.java.com.doctor_appointments.availability.business;

import main.java.com.doctor_appointments.availability.data.SlotEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotDto(UUID slotId, UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {
    public static SlotDto fromRepo(SlotEntity slotEntity) {
        return new SlotDto(slotEntity.slotId(),
                slotEntity.doctorId(),
                slotEntity.doctorName(),
                slotEntity.time(),
                slotEntity.isReserved(),
                slotEntity.cost());
    }
}
