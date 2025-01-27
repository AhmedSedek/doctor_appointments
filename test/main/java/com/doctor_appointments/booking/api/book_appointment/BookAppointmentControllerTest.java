package main.java.com.doctor_appointments.booking.api.book_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotRequest;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotResponse;
import main.java.com.doctor_appointments.booking.application.AppointmentService;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookAppointmentControllerTest {
  private BookAppointmentController controller;
  private IAppointmentService service;
  private IAppointmentRepo repo;

  private ReserveSlotController mockReserveSlotController;

  @BeforeEach
  void setUp() {
    INotificationService mockNotificationService = mock(INotificationService.class);
    ListSlotsController mockListSlotsController = mock(ListSlotsController.class);
    ReleaseSlotController mockReleaseSlotController = mock(ReleaseSlotController.class);
    mockReserveSlotController = mock(ReserveSlotController.class);
    repo = new InMemoryAppointmentRepo(mockNotificationService);
    service = new AppointmentService(repo, mockListSlotsController, mockReleaseSlotController, mockReserveSlotController);
    controller = new BookAppointmentController(service);
  }


  @Test
  void testHandle_storesExpectedAppointment() throws Exception {
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    when(mockReserveSlotController.handle(new ReserveSlotRequest(slotId))).thenReturn(new ReserveSlotResponse());
    BookAppointmentRequest request = new BookAppointmentRequest(slotId, patientId, patientName);

    BookAppointmentResponse response = controller.handle(request);

    AppointmentEntity storedAppointment = repo
        .listAppointments()
        .stream()
        .filter(appointment -> appointment.appointmentId().equals(response.appointmentId()))
        .findAny()
        .get();
    assertEquals(slotId, storedAppointment.slotId());
    assertEquals(patientId, storedAppointment.patientId());
    assertEquals(patientName, storedAppointment.patientName());
  }

  @Test
  void testHandle_reservesSlot() throws Exception {
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    BookAppointmentRequest request = new BookAppointmentRequest(slotId, patientId, patientName);

    controller.handle(request);

    verify(mockReserveSlotController).handle(eq(new ReserveSlotRequest(slotId)));
  }
}
