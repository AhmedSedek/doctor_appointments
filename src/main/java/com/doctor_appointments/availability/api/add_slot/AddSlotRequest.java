package main.java.com.doctor_appointments.availability.api.add_slot;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddSlotRequest(UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost) {}
