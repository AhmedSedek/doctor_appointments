package main.java.com.doctor_appointments.availability.api.list_slots;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.repository.InMemorySlotRepo;
import main.java.com.doctor_appointments.availability.service.DoctorAvailabilityService;
import main.java.com.doctor_appointments.availability.service.SlotDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListSlotsControllerTest {

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

  private ListSlotsController controller;
  private ISlotRepo slotRepo;

  @BeforeEach
  void setUp() {
    slotRepo = new InMemorySlotRepo();
    controller = new ListSlotsController(new DoctorAvailabilityService(slotRepo));
  }


  @Test
  void testListSlots_allSlots() throws Exception {
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);
    ListSlotsRequest request = new ListSlotsRequest(/*availableOnly=*/false);

    ListSlotsResponse response = controller.handle(request);

    assertEquals(2, response.slots().size());
    assertTrue(
        response
            .slots()
            .containsAll(
                List.of(
                    SlotDto.fromRepo(ARBITRARY_RESERVED_SLOT).toSharedSlot(),
                    SlotDto.fromRepo(ARBITRARY_UNRESERVED_SLOT).toSharedSlot()))
    );
  }

  @Test
  void testListSlots_availableOnly() throws Exception {
    slotRepo.addSlot(ARBITRARY_RESERVED_SLOT);
    slotRepo.addSlot(ARBITRARY_UNRESERVED_SLOT);
    ListSlotsRequest request = new ListSlotsRequest(/*availableOnly=*/true);

    ListSlotsResponse response = controller.handle(request);

    assertEquals(1, response.slots().size());
    assertTrue(response.slots().contains(SlotDto.fromRepo(ARBITRARY_UNRESERVED_SLOT).toSharedSlot()));
  }
}
