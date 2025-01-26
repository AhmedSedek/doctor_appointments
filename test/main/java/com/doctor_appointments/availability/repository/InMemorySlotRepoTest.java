package main.java.com.doctor_appointments.availability.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyExistsException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReleasedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemorySlotRepoTest {

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

  private InMemorySlotRepo repo;

  @BeforeEach
  void setUp() {
    repo = new InMemorySlotRepo();
  }

  @Test
  void testAddSlot_singleSlotStored() throws Exception {
    repo.addSlot(ARBITRARY_RESERVED_SLOT);

    List<SlotEntity> storedSlots = repo.listSlots();
    assertEquals(1, storedSlots.size());
    assertTrue(storedSlots.contains(ARBITRARY_RESERVED_SLOT));
  }

  @Test
  void testAddSlot_multipleSlotsStored() throws Exception {
    repo.addSlot(ARBITRARY_RESERVED_SLOT);
    repo.addSlot(ARBITRARY_UNRESERVED_SLOT);

    List<SlotEntity> storedSlots = repo.listSlots();
    assertEquals(2, storedSlots.size());
    assertTrue(storedSlots.containsAll(List.of(ARBITRARY_RESERVED_SLOT, ARBITRARY_UNRESERVED_SLOT)));
  }

  @Test
  void testAddSlot_duplicateSlotId_throws() throws Exception {
    repo.addSlot(ARBITRARY_UNRESERVED_SLOT);

    assertThrows(SlotAlreadyExistsException.class, () -> repo.addSlot(ARBITRARY_UNRESERVED_SLOT));
  }

  @Test
  void testReleaseSlot_reservedSlot() throws Exception {
    repo.addSlot(ARBITRARY_RESERVED_SLOT);
    SlotEntity expectedSlot =  SlotEntity.withReserved(ARBITRARY_RESERVED_SLOT, /*isReserved=*/false);

    repo.releaseSlot(ARBITRARY_RESERVED_SLOT.slotId());

    List<SlotEntity> storedSlots = repo.listSlots();
    assertEquals(1, storedSlots.size());
    assertTrue(storedSlots.contains(expectedSlot));
  }

  @Test
  void testReleaseSlot_releasedSlot_throws() throws Exception {
    SlotEntity releasedSlot = ARBITRARY_UNRESERVED_SLOT;
    repo.addSlot(releasedSlot);

    assertThrows(SlotAlreadyReleasedException.class, () ->repo.releaseSlot(releasedSlot.slotId()));
  }

  @Test
  void testReleaseSlot_slotNotFound_throws() throws Exception {
    // Do not insert slot.
    assertThrows(SlotNotFoundException.class, () -> repo.releaseSlot(UUID.randomUUID()));
  }

  @Test
  void testReserveSlot() throws Exception {
    repo.addSlot(ARBITRARY_UNRESERVED_SLOT);
    SlotEntity expectedSlot =  SlotEntity.withReserved(ARBITRARY_UNRESERVED_SLOT, /*isReserved=*/true);

    repo.reserveSlot(ARBITRARY_UNRESERVED_SLOT.slotId());

    List<SlotEntity> storedSlots = repo.listSlots();
    assertEquals(1, storedSlots.size());
    assertTrue(storedSlots.contains(expectedSlot));
  }

  @Test
  void testReserveSlot_reservedSlot_throws() throws Exception {
    SlotEntity reservedSlot = ARBITRARY_RESERVED_SLOT;
    repo.addSlot(reservedSlot);

    assertThrows(SlotAlreadyReservedException.class, () ->repo.reserveSlot(reservedSlot.slotId()));
  }

  @Test
  void testReserveSlot_slotNotFound_throws() throws Exception {
    // Do not insert slot.
    assertThrows(SlotNotFoundException.class, () -> repo.reserveSlot(UUID.randomUUID()));
  }
}
