package main.java.com.doctor_appointments.booking.api.list_appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentController;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentRequest;
import main.java.com.doctor_appointments.booking.application.AppointmentDto;
import main.java.com.doctor_appointments.booking.application.AppointmentService;
import main.java.com.doctor_appointments.booking.application.IAppointmentService;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListAppointmentsControllerTest {
  private ListAppointmentsController controller;
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
    controller = new ListAppointmentsController(service);
  }


  @Test
  void testHandle() throws Exception {
    UUID appointmentId = UUID.randomUUID();
    UUID appointmentId2 = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID slotId2 = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservationTime = LocalDateTime.now();
    AppointmentEntity appointment1 = new AppointmentEntity(appointmentId, slotId, patientId, patientName, reservationTime);
    AppointmentEntity appointment2 = new AppointmentEntity(appointmentId2, slotId2, patientId, patientName, reservationTime);
    repo.add(appointment1);
    repo.add(appointment2);

    ListAppointmentsResponse response = controller.handle(new ListAppointmentsRequest());

    assertEquals(2, response.appointments().size());
    assertTrue(
        response
            .appointments()
            .containsAll(
                List.of(
                  AppointmentDto.fromRepo(appointment2).toSharedAppointment(),
                  AppointmentDto.fromRepo(appointment1).toSharedAppointment())));
  }
}
