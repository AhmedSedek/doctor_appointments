package main.java.com.doctor_appointments.availability.api.add_slot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.repository.ISlotRepo;
import main.java.com.doctor_appointments.availability.repository.InMemorySlotRepo;
import main.java.com.doctor_appointments.availability.service.DoctorAvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AddSlotControllerTest {
  private AddSlotController controller;
  private ISlotRepo slotRepo;

  @BeforeEach
  void setUp() {
    slotRepo = new InMemorySlotRepo();
    controller = new AddSlotController(new DoctorAvailabilityService(slotRepo));
  }


  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testAddSlot_storesExpectedSlot(boolean isReserved) throws Exception {
    UUID doctorId = UUID.randomUUID();
    String doctorName = "Dr.Magnificent";
    LocalDateTime time = LocalDateTime.now();
    Double cost = 200.00;
    AddSlotRequest request = new AddSlotRequest(doctorId, doctorName, time, isReserved, cost);

    AddSlotResponse response = controller.handle(request);

    SlotEntity storedSlot = slotRepo.listSlots().stream().filter(slot -> slot.slotId().equals(response.slotId())).findAny().get();
    assertEquals(doctorId, storedSlot.doctorId());
    assertEquals(doctorName, storedSlot.doctorName());
    assertEquals(time, storedSlot.time());
    assertEquals(isReserved, storedSlot.isReserved());
    assertEquals(cost, storedSlot.cost());
  }
}
