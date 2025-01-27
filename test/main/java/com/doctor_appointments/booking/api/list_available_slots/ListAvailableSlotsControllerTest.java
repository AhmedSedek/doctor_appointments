package main.java.com.doctor_appointments.booking.api.list_available_slots;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsResponse;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.availability.shared.Slot;
import main.java.com.doctor_appointments.booking.application.AppointmentService;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListAvailableSlotsControllerTest {
  private ListAvailableSlotsController controller;
  private IAppointmentService service;
  private IAppointmentRepo repo;

  private ListSlotsController mockListSlotsController;

  @BeforeEach
  void setUp() {
    INotificationService mockNotificationService = mock(INotificationService.class);
    mockListSlotsController = mock(ListSlotsController.class);
    ReleaseSlotController mockReleaseSlotController = mock(ReleaseSlotController.class);
    ReserveSlotController mockReserveSlotController = mock(ReserveSlotController.class);
    repo = new InMemoryAppointmentRepo(mockNotificationService);
    service = new AppointmentService(repo, mockListSlotsController, mockReleaseSlotController, mockReserveSlotController);
    controller = new ListAvailableSlotsController(service);
  }


  @Test
  void testHandle() throws Exception {
    UUID slotId1 = UUID.randomUUID();
    UUID slotId2 = UUID.randomUUID();
    UUID doctorId = UUID.randomUUID();
    String doctorName = "Dr.Doctor";
    LocalDateTime time = LocalDateTime.now();
    Double cost = 200.00;
    Slot slot1 = new Slot(slotId1, doctorId, doctorName, time, /*isReserved=*/false, cost);
    Slot slot2 = new Slot(slotId2, doctorId, doctorName, time, /*isReserved=*/false, cost);
    when(mockListSlotsController.handle(any())).thenReturn(new ListSlotsResponse(List.of(slot1, slot2)));

    ListAvailableSlotsResponse response = controller.handle(new ListAvailableSlotsRequest());

    assertEquals(2, response.slots().size());
    assertTrue(response.slots().containsAll(List.of(slot2, slot1)));

    assertEquals(0, repo.listAppointments().size());
  }
}
