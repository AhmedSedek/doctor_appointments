package main.java.com.doctor_appointments.availability.shared;

import java.time.LocalDateTime;
import java.util.UUID;

public record Slot(UUID slotId, UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {}
