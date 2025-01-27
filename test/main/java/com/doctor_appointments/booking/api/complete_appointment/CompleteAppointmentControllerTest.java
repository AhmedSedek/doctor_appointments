package main.java.com.doctor_appointments.booking.api.complete_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotRequest;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotResponse;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.application.AppointmentService;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompleteAppointmentControllerTest {
  private CompleteAppointmentController controller;
  private IAppointmentService service;
  private IAppointmentRepo repo;

  @BeforeEach
  void setUp() {
    INotificationService mockNotificationService = mock(INotificationService.class);
    ListSlotsController mockListSlotsController = mock(ListSlotsController.class);
    ReleaseSlotController mockReleaseSlotController = mock(ReleaseSlotController.class);
    ReserveSlotController mockReserveSlotController = mock(ReserveSlotController.class);
    repo = new InMemoryAppointmentRepo(mockNotificationService);
    service = new AppointmentService(repo, mockListSlotsController, mockReleaseSlotController, mockReserveSlotController);
    controller = new CompleteAppointmentController(service);
  }


  @Test
  void testHandle_removesAppointment() throws Exception {
    UUID appointmentId = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservationTime = LocalDateTime.now();
    repo.add(new AppointmentEntity(appointmentId, slotId, patientId, patientName, reservationTime));

    controller.handle(new CompleteAppointmentRequest(appointmentId));

    assertEquals(0, repo.listAppointments().size());
  }
}
