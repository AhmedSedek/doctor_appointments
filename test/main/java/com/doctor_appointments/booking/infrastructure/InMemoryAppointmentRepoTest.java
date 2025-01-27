package main.java.com.doctor_appointments.booking.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentAlreadyExistsException;
import main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException;
import main.java.com.doctor_appointments.confirmation.INotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryAppointmentRepoTest {

  private static UUID ARBITRARY_APPOINTMENT_ID = UUID.randomUUID();
  private static UUID ARBITRARY_APPOINTMENT_ID_2 = UUID.randomUUID();
  private static UUID ARBITRARY_SLOT_ID = UUID.randomUUID();
  private static UUID ARBITRARY_SLOT_ID_2 = UUID.randomUUID();
  private static UUID ARBITRARY_PATIENT_ID = UUID.randomUUID();
  private static String ARBITRARY_PATIENT_NAME = "Dr.Arbitrary";
  private static LocalDateTime ARBITRARY_APPOINTMENT_TIME = LocalDateTime.of(2025, 01, 26, 20, 41);

  private static AppointmentEntity ARBITRARY_APPOINTMENT = new AppointmentEntity(
      ARBITRARY_APPOINTMENT_ID,
      ARBITRARY_SLOT_ID,
      ARBITRARY_PATIENT_ID,
      ARBITRARY_PATIENT_NAME,
      ARBITRARY_APPOINTMENT_TIME);
  private static AppointmentEntity ARBITRARY_APPOINTMENT_2 = new AppointmentEntity(
      ARBITRARY_APPOINTMENT_ID_2,
      ARBITRARY_SLOT_ID_2,
      ARBITRARY_PATIENT_ID,
      ARBITRARY_PATIENT_NAME,
      ARBITRARY_APPOINTMENT_TIME);

  private InMemoryAppointmentRepo repo;
  private INotificationService mockNotificationService;

  @BeforeEach
  void setUp() {
    mockNotificationService = mock(INotificationService.class);
    repo = new InMemoryAppointmentRepo(mockNotificationService);
  }

  @Test
  void testAdd_singleAppointmentStored() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    List<AppointmentEntity> storedAppointments = repo.listAppointments();
    assertEquals(1, storedAppointments.size());
    assertTrue(storedAppointments.contains(ARBITRARY_APPOINTMENT));
  }

  @Test
  void testAdd_multipleAppointmentsStored() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);
    repo.add(ARBITRARY_APPOINTMENT_2);

    List<AppointmentEntity> storedAppointments = repo.listAppointments();
    assertEquals(2, storedAppointments.size());
    assertTrue(storedAppointments.containsAll(List.of(ARBITRARY_APPOINTMENT, ARBITRARY_APPOINTMENT_2)));
  }

  @Test
  void testAdd_duplicateAppointmentId_throws() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    assertThrows(AppointmentAlreadyExistsException.class, () -> repo.add(ARBITRARY_APPOINTMENT));
  }

  @Test
  void testAdd_sendsDoctorNotification() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    verify(mockNotificationService).notify("doctor@email.com", ARBITRARY_APPOINTMENT.toString());
  }

  @Test
  void testAdd_sendsPatientNotification() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    verify(mockNotificationService).notify(String.format("%s@email.com", ARBITRARY_PATIENT_ID), ARBITRARY_APPOINTMENT.toString());
  }

  @Test
  void testComplete() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    repo.complete(ARBITRARY_APPOINTMENT.appointmentId());

    List<AppointmentEntity> storedAppointments = repo.listAppointments();
    assertEquals(0, storedAppointments.size());
  }

  @Test
  void testComplete_alreadyCompleted() throws Exception {
    AppointmentEntity appointment = ARBITRARY_APPOINTMENT;
    UUID appointmentId = ARBITRARY_APPOINTMENT.appointmentId();
    repo.add(appointment);
    repo.complete(appointmentId);

    assertDoesNotThrow(() -> repo.complete(appointmentId));
  }

  @Test
  void testComplete_appointmentNotFound_throws() {
    // Do not insert appointment
    assertThrows(AppointmentNotFoundException.class, () -> repo.complete(UUID.randomUUID()));
  }

  @Test
  void testDelete() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);

    repo.delete(ARBITRARY_APPOINTMENT.appointmentId());

    List<AppointmentEntity> storedAppointments = repo.listAppointments();
    assertEquals(0, storedAppointments.size());
  }

  @Test
  void testDelete_notFoundAppointment_throws() {
    assertThrows(AppointmentNotFoundException.class, () -> repo.delete(UUID.randomUUID()));
  }

  @Test
  void testList_listsNonCompletedAppointments() throws Exception {
    repo.add(ARBITRARY_APPOINTMENT);
    repo.add(ARBITRARY_APPOINTMENT_2);
    repo.complete(ARBITRARY_APPOINTMENT.appointmentId());

    List<AppointmentEntity> nonCompletedAppointments = repo.listAppointments();

    assertEquals(1, nonCompletedAppointments.size());
    assertTrue(nonCompletedAppointments.contains(ARBITRARY_APPOINTMENT_2));
  }
}
