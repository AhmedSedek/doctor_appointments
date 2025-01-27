package main.java.com.doctor_appointments.availability.api.release_slot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.repository.InMemorySlotRepo;
import main.java.com.doctor_appointments.availability.service.DoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReleasedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReleaseSlotControllerTest {

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

  private ReleaseSlotController controller;
  private ISlotRepo slotRepo;

  @BeforeEach
  void setUp() {
    slotRepo = new InMemorySlotRepo();
    controller = new ReleaseSlotController(new DoctorAvailabilityService(slotRepo));
  }

  @Test
  void testReleaseSlot() throws Exception {
    UUID slotId = ARBITRARY_RESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    ReleaseSlotRequest request = new ReleaseSlotRequest(slotId);

    controller.handle(request);

    SlotEntity releasedSlot = slotRepo.listSlots().stream().filter(slotEntity -> slotEntity.slotId().equals(slotId)).findAny().get();
    assertFalse(releasedSlot.isReserved());
  }

  @Test
  void testReleaseSlot_notFoundSlot_throws() throws Exception {
    ReleaseSlotRequest request = new ReleaseSlotRequest(UUID.randomUUID());

    assertThrows(SlotNotFoundException.class, () -> controller.handle(request));
  }

  @Test
  void testReleaseSlot_slotAlreadyReleased_throws() throws Exception {
    UUID slotId = ARBITRARY_UNRESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);
    ReleaseSlotRequest request = new ReleaseSlotRequest(slotId);

    assertThrows(SlotAlreadyReleasedException.class, () -> controller.handle(request));
  }
}
