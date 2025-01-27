package main.java.com.doctor_appointments.booking.api.cancel_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotRequest;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotResponse;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.booking.application.AppointmentService;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelAppointmentControllerTest {
  private CancelAppointmentController controller;
  private IAppointmentService service;
  private IAppointmentRepo repo;

  private ReleaseSlotController mockReleaseSlotController;

  @BeforeEach
  void setUp() {
    INotificationService mockNotificationService = mock(INotificationService.class);
    ListSlotsController mockListSlotsController = mock(ListSlotsController.class);
    mockReleaseSlotController = mock(ReleaseSlotController.class);
    ReserveSlotController mockReserveSlotController = mock(ReserveSlotController.class);
    repo = new InMemoryAppointmentRepo(mockNotificationService);
    service = new AppointmentService(repo, mockListSlotsController, mockReleaseSlotController, mockReserveSlotController);
    controller = new CancelAppointmentController(service);
  }


  @Test
  void testHandle_deletesAppointment() throws Exception {
    UUID appointmentId = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservationTime = LocalDateTime.now();
    repo.add(new AppointmentEntity(appointmentId, slotId, patientId, patientName, reservationTime));
    when(mockReleaseSlotController.handle(new ReleaseSlotRequest(slotId))).thenReturn(new ReleaseSlotResponse());

    controller.handle(new CancelAppointmentRequest(appointmentId));

    assertEquals(0, repo.listAppointments().size());
  }

  @Test
  void testHandle_releasesSlot() throws Exception {
    UUID appointmentId = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservationTime = LocalDateTime.now();
    repo.add(new AppointmentEntity(appointmentId, slotId, patientId, patientName, reservationTime));

    controller.handle(new CancelAppointmentRequest(appointmentId));

    verify(mockReleaseSlotController).handle(eq(new ReleaseSlotRequest(slotId)));
  }
}
