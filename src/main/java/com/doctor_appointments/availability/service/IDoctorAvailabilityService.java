package main.java.com.doctor_appointments.availability.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IDoctorAvailabilityService {
  SlotDto addSlot(UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost);

  List<SlotDto> listSlots(boolean availableOnly);

  void releaseSlot(UUID slotId);

  void reserveSlot(UUID slotId);
}
