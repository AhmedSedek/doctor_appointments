package main.java.com.doctor_appointments.availability.api.reserve_slot;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.repository.InMemorySlotRepo;
import main.java.com.doctor_appointments.availability.service.DoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReserveSlotControllerTest {

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

  private ReserveSlotController controller;
  private ISlotRepo slotRepo;

  @BeforeEach
  void setUp() {
    slotRepo = new InMemorySlotRepo();
    controller = new ReserveSlotController(new DoctorAvailabilityService(slotRepo));
  }

  @Test
  void testReserveSlot() throws Exception {
    UUID slotId = ARBITRARY_UNRESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);
    ReserveSlotRequest request = new ReserveSlotRequest(slotId);

    controller.handle(request);

    SlotEntity reservedSlot = slotRepo.listSlots().stream().filter(slotEntity -> slotEntity.slotId().equals(slotId)).findAny().get();
    assertTrue(reservedSlot.isReserved());
  }

  @Test
  void testReserveSlot_notFoundSlot_throws() throws Exception {
    ReserveSlotRequest request = new ReserveSlotRequest(UUID.randomUUID());

    assertThrows(SlotNotFoundException.class, () -> controller.handle(request));
  }

  @Test
  void testReserveSlot_slotAlreadyReserved_throws() throws Exception {
    UUID slotId = ARBITRARY_RESERVED_SLOT.slotId();
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    ReserveSlotRequest request = new ReserveSlotRequest(slotId);

    assertThrows(SlotAlreadyReservedException.class, () -> controller.handle(request));
  }
}
