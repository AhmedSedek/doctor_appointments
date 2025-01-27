package main.java.com.doctor_appointments.booking.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsController;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsRequest;
import main.java.com.doctor_appointments.availability.api.list_slots.ListSlotsResponse;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotController;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotRequest;
import main.java.com.doctor_appointments.availability.api.release_slot.ReleaseSlotResponse;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotController;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotRequest;
import main.java.com.doctor_appointments.availability.api.reserve_slot.ReserveSlotResponse;
import main.java.com.doctor_appointments.availability.shared.Slot;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.domain.IAppointmentRepo;
import main.java.com.doctor_appointments.booking.infrastructure.InMemoryAppointmentRepo;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotAlreadyBookedException;
import main.java.com.doctor_appointments.booking.shared.exceptions.SlotNotFoundException;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentServiceTest {

  private static final UUID APPOINTMENT_ID = UUID.randomUUID();
  private static final UUID SLOT_ID = UUID.randomUUID();
  private static final UUID PATIENT_ID = UUID.randomUUID();
  private static final String PATIENT_NAME = "some_patient";
  private static final LocalDateTime RESERVATION_TIME = LocalDateTime.of(2025, 1, 27, 19, 9);

  private static final AppointmentEntity APPOINTMENT = new AppointmentEntity(
      APPOINTMENT_ID, SLOT_ID, PATIENT_ID, PATIENT_NAME, RESERVATION_TIME);

  private AppointmentService appointmentService;
  private IAppointmentRepo appointmentRepo;

  private INotificationService mockNotificationService;
  private ListSlotsController mockListSlotsController;
  private ReleaseSlotController mockReleaseSlotController;
  private ReserveSlotController mockReserveSlotController;

  @BeforeEach
  void setUp() {
    mockNotificationService = mock(INotificationService.class);
    mockListSlotsController = mock(ListSlotsController.class);
    mockReleaseSlotController = mock(ReleaseSlotController.class);
    mockReserveSlotController = mock(ReserveSlotController.class);

    appointmentRepo = new InMemoryAppointmentRepo(mockNotificationService);
    appointmentService = new AppointmentService(appointmentRepo, mockListSlotsController, mockReleaseSlotController, mockReserveSlotController);
  }

  @Test
  void testBookAppointment_storesExpectedAppointment() throws Exception {
    mockSuccessfulReserveSlot(SLOT_ID);

    AppointmentDto returnedAppointment = appointmentService.bookAppointment(SLOT_ID, PATIENT_ID, PATIENT_NAME);

    AppointmentEntity storedAppointment = appointmentRepo
        .listAppointments()
        .stream()
        .filter(appointmentEntity -> appointmentEntity.appointmentId().equals(returnedAppointment.appointmentId()))
        .findAny()
        .get();
    assertEquals(SLOT_ID, storedAppointment.slotId());
    assertEquals(PATIENT_ID, storedAppointment.patientId());
    assertEquals(PATIENT_NAME, storedAppointment.patientName());
  }

  @Test
  void testBookAppointment_slotAlreadyReserved_throws() throws Exception {
    mockFailedReserveSlot(SLOT_ID, SlotAlreadyReservedException.class);

    assertThrows(
        SlotAlreadyBookedException.class,
        () -> appointmentService.bookAppointment(SLOT_ID, PATIENT_ID, PATIENT_NAME));
  }

  @Test
  void testBookAppointment_slotNotFound_throws() throws Exception {
    mockFailedReserveSlot(
        SLOT_ID,
        main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException.class);

    assertThrows(
        SlotNotFoundException.class,
        () -> appointmentService.bookAppointment(SLOT_ID, PATIENT_ID, PATIENT_NAME));
  }

  @Test
  void testCancelAppointment() throws Exception {
    appointmentRepo.add(APPOINTMENT);
    mockSuccessfulReleaseSlot(APPOINTMENT.slotId());

    appointmentService.cancelAppointment(APPOINTMENT.appointmentId());

    assertEquals(0, appointmentRepo.listAppointments().size());
  }

  @Test
  void testCancelAppointment_appointmentNotFound_throws() throws Exception {
    // Do not add appointment to the repo.
    assertThrows(AppointmentNotFoundException.class, () -> appointmentService.cancelAppointment(UUID.randomUUID()));
  }

  @Test
  void testCompleteAppointment() throws Exception {
    appointmentRepo.add(APPOINTMENT);

    appointmentService.completeAppointment(APPOINTMENT.appointmentId());

    assertEquals(0, appointmentRepo.listAppointments().size());
  }

  @Test
  void testCompleteAppointment_appointmentAlreadyCompleted() throws Exception {
    appointmentRepo.add(APPOINTMENT);
    appointmentService.completeAppointment(APPOINTMENT.appointmentId());

    assertDoesNotThrow(() -> appointmentService.completeAppointment(APPOINTMENT.appointmentId()));
  }

  @Test
  void testCompleteAppointment_appointmentNotFound_throws() throws Exception {
    assertThrows(AppointmentNotFoundException.class, () -> appointmentService.completeAppointment(UUID.randomUUID()));
  }

  @Test
  void testListAppointments() throws Exception {
    AppointmentEntity appointment1 = APPOINTMENT;
    UUID appointmentId2 = UUID.randomUUID();
    AppointmentEntity appointment2 = new AppointmentEntity(appointmentId2, SLOT_ID, PATIENT_ID, PATIENT_NAME, RESERVATION_TIME);
    appointmentRepo.add(appointment1);
    appointmentRepo.add(appointment2);

    List<AppointmentDto> returnedAppointments = appointmentService.listAppointments();

    assertEquals(2, returnedAppointments.size());
    assertTrue(returnedAppointments.containsAll(List.of(AppointmentDto.fromRepo(appointment1), AppointmentDto.fromRepo(appointment2))));
  }

  @Test
  void testListAvailableSlots() {
    Slot availableSlot1 = new Slot(UUID.randomUUID(), UUID.randomUUID(), "", LocalDateTime.now(), /*isReserved=*/false, 0.0);
    Slot availableSlot2 = new Slot(UUID.randomUUID(), UUID.randomUUID(), "", LocalDateTime.now(), /*isReserved=*/false, 0.0);
    mockSuccessfulListAvailableSlots(List.of(availableSlot1, availableSlot2));

    List<Slot> returnedSlots = appointmentService.listAvailableSlots();

    assertEquals(2, returnedSlots.size());
    assertTrue(returnedSlots.containsAll(List.of(availableSlot1, availableSlot2)));
  }

  @Test
  private void mockSuccessfulListAvailableSlots(List<Slot> slots) {
    when(mockListSlotsController.handle(new ListSlotsRequest(/*availableOnly=*/true))).thenReturn(
        new ListSlotsResponse(slots));
  }

  private void mockSuccessfulReleaseSlot(UUID slotId) throws Exception {
    when(mockReleaseSlotController.handle(new ReleaseSlotRequest(slotId))).thenReturn(new ReleaseSlotResponse());
  }

  private void mockFailedReleaseSlot(UUID slotId, Class<? extends Throwable> e) throws Exception {
    when(mockReleaseSlotController.handle(new ReleaseSlotRequest(slotId))).thenThrow(e);
  }

  private void mockSuccessfulReserveSlot(UUID slotId) throws Exception {
    when(mockReserveSlotController.handle(new ReserveSlotRequest(slotId))).thenReturn(new ReserveSlotResponse());
  }

  private void mockFailedReserveSlot(UUID slotId, Class<? extends Throwable> e) throws Exception {
    when(mockReserveSlotController.handle(new ReserveSlotRequest(slotId))).thenThrow(e);
  }
}
