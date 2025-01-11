package main.java.com.doctor_appointments.availability.data;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotEntity(UUID slotId, UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {
}
