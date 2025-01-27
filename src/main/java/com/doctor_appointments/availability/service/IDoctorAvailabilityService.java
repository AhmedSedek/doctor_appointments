package main.java.com.doctor_appointments.availability.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyExistsException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;

public interface IDoctorAvailabilityService {
  SlotDto addSlot(UUID doctorId, String doctorName, LocalDateTime time, boolean isReserved, Double cost)
      throws SlotAlreadyExistsException;

  List<SlotDto> listSlots(boolean availableOnly);

  void releaseSlot(UUID slotId);

  void reserveSlot(UUID slotId) throws SlotAlreadyReservedException, SlotNotFoundException;
}
