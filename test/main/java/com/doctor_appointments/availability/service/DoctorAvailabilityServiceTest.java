package main.java.com.doctor_appointments.availability.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.repository.InMemorySlotRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DoctorAvailabilityServiceTest {

  private static UUID ARBITRARY_SLOT_ID = UUID.randomUUID();
  private static UUID ARBITRARY_SLOT_ID_2 = UUID.randomUUID();
  private static UUID ARBITRARY_DOCTOR_ID = UUID.randomUUID();
  private static String ARBITRARY_DOCTOR_NAME = "Dr.Arbitrary";
  private static LocalDateTime ARBITRARY_SLOT_TIME = LocalDateTime.of(2025, 01, 25, 19, 58);
  private static Double ARBITRARY_COST = 199.99;

  private static SlotEntity ARBITRARY_UNRESERVED_SLOT = new SlotEntity(
      ARBITRARY_SLOT_ID,
      ARBITRARY_DOCTOR_ID,
      ARBITRARY_DOCTOR_NAME,
      ARBITRARY_SLOT_TIME,
      /*isReserved=*/false,
      ARBITRARY_COST);
  private static SlotEntity ARBITRARY_RESERVED_SLOT = new SlotEntity(
      ARBITRARY_SLOT_ID_2,
      ARBITRARY_DOCTOR_ID,
      ARBITRARY_DOCTOR_NAME,
      ARBITRARY_SLOT_TIME,
      /*isReserved=*/true,
      ARBITRARY_COST);

  private DoctorAvailabilityService service;
  private ISlotRepo slotRepo;

  @BeforeEach
  void setUp() {
    slotRepo = new InMemorySlotRepo();
    service = new DoctorAvailabilityService(slotRepo);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testAddSlot_storesExpectedSlot(boolean isReserved) throws Exception {
    UUID doctorId = UUID.randomUUID();
    String doctorName = "Dr.Magnificent";
    LocalDateTime time = LocalDateTime.now();
    Double cost = 200.00;

    SlotDto returnedSlot = service.addSlot(doctorId, doctorName, time, isReserved, cost);

    SlotEntity storedSlot = slotRepo.listSlots().stream().filter(slot -> slot.slotId().equals(returnedSlot.slotId())).findAny().get();
    assertEquals(doctorId, storedSlot.doctorId());
    assertEquals(doctorName, storedSlot.doctorName());
    assertEquals(time, storedSlot.time());
    assertEquals(isReserved, storedSlot.isReserved());
    assertEquals(cost, storedSlot.cost());
  }

  @Test
  void testListSlots_allSlots() throws Exception {
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);

    List<SlotDto> storedSlots = service.listSlots(/*availableOnly=*/false);

    assertEquals(2, storedSlots.size());
    assertTrue(storedSlots.containsAll(List.of(SlotDto.fromRepo(ARBITRARY_RESERVED_SLOT), SlotDto.fromRepo(ARBITRARY_UNRESERVED_SLOT))));
  }

  @Test
  void testListSlots_availableOnly() throws Exception {
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);

    List<SlotDto> storedSlots = service.listSlots(/*availableOnly=*/true);

    assertEquals(1, storedSlots.size());
    assertTrue(storedSlots.contains(SlotDto.fromRepo(ARBITRARY_UNRESERVED_SLOT)));
  }

  @Test
  void testReleaseSlot() throws Exception {
    UUID slotId = ARBITRARY_RESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);

    service.releaseSlot(slotId);

    SlotEntity releasedSlot = slotRepo.listSlots().stream().filter(slotEntity -> slotEntity.slotId().equals(slotId)).findAny().get();
    assertFalse(releasedSlot.isReserved());
  }

  @Test
  void testReserveSlot() throws Exception {
    UUID slotId = ARBITRARY_UNRESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);

    service.reserveSlot(slotId);

    SlotEntity releasedSlot = slotRepo.listSlots().stream().filter(slotEntity -> slotEntity.slotId().equals(slotId)).findAny().get();
    assertTrue(releasedSlot.isReserved());
  }
}
