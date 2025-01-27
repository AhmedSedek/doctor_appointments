package main.java.com.doctor_appointments.availability.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.shared.Slot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SlotDtoTest {

  @ParameterizedTest
  @ValueSource(booleans =  {true, false})
  void testFromRepo(boolean isReserved) {
    UUID slotId = UUID.randomUUID();
    UUID doctorId = UUID.randomUUID();
    String doctorName = "some_doctor";
    LocalDateTime time = LocalDateTime.now();
    Double cost = 129.99;
    SlotEntity slotEntity = new SlotEntity(slotId, doctorId, doctorName, time, isReserved, cost);
    SlotDto expectedSlotDto = new SlotDto(slotId, doctorId, doctorName, time, isReserved, cost);

    SlotDto convertedDto = SlotDto.fromRepo(slotEntity);

    assertEquals(expectedSlotDto, convertedDto);
  }

  @ParameterizedTest
  @ValueSource(booleans =  {true, false})
  void testToShared(boolean isReserved) {
    UUID slotId = UUID.randomUUID();
    UUID doctorId = UUID.randomUUID();
    String doctorName = "some_doctor";
    LocalDateTime time = LocalDateTime.now();
    Double cost = 129.99;
    SlotDto slotDto = new SlotDto(slotId, doctorId, doctorName, time, isReserved, cost);
    Slot expectedSlot = new Slot(slotId, doctorId, doctorName, time, isReserved, cost);

    Slot convertedSlot = slotDto.toSharedSlot();

    assertEquals(expectedSlot, convertedSlot);
  }

}
