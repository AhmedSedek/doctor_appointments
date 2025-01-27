package main.java.com.doctor_appointments.management.core.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentController;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.cancel_appointment.CancelAppointmentResponse;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentController;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentRequest;
import main.java.com.doctor_appointments.booking.api.complete_appointment.CompleteAppointmentResponse;
import main.java.com.doctor_appointments.booking.api.list_appointments.ListAppointmentsController;
import main.java.com.doctor_appointments.booking.api.list_appointments.ListAppointmentsRequest;
import main.java.com.doctor_appointments.booking.api.list_appointments.ListAppointmentsResponse;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import main.java.com.doctor_appointments.management.core.exceptions.AppointmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementServiceTest {

  private static final UUID APPOINTMENT_ID = UUID.randomUUID();

  private ManagementService managementService;

  private CancelAppointmentController mockCancelAppointmentController;
  private CompleteAppointmentController mockCompleteAppointmentController;
  private ListAppointmentsController mockListAppointmentsController;

  @BeforeEach
  void setUp() {
    mockCancelAppointmentController = mock(CancelAppointmentController.class);
    mockCompleteAppointmentController = mock(CompleteAppointmentController.class);
    mockListAppointmentsController = mock(ListAppointmentsController.class);

    managementService = new ManagementService(
        mockCancelAppointmentController,
        mockCompleteAppointmentController,
        mockListAppointmentsController);
  }

  @Test
  void testCancelAppointment() throws Exception {
    when(mockCancelAppointmentController.handle(new CancelAppointmentRequest(APPOINTMENT_ID))).thenReturn(
        new CancelAppointmentResponse());

    assertDoesNotThrow(() -> managementService.cancelAppointment(APPOINTMENT_ID));
  }

  @Test
  void testCancelAppointment_appointmentNotFound() throws Exception {
    when(mockCancelAppointmentController.handle(new CancelAppointmentRequest(APPOINTMENT_ID))).thenThrow(
        main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException.class);


    assertDoesNotThrow(() -> managementService.cancelAppointment(APPOINTMENT_ID));
  }

  @Test
  void testCompleteAppointment() throws Exception {
    when(mockCompleteAppointmentController.handle(new CompleteAppointmentRequest(APPOINTMENT_ID))).thenReturn(
        new CompleteAppointmentResponse());

    assertDoesNotThrow(() -> managementService.completeAppointment(APPOINTMENT_ID));
  }

  @Test
  void testCompleteAppointment_appointmentNotFound_throws() throws Exception {
    when(mockCompleteAppointmentController.handle(new CompleteAppointmentRequest(APPOINTMENT_ID))).thenThrow(
        main.java.com.doctor_appointments.booking.shared.exceptions.AppointmentNotFoundException.class);

    assertThrows(AppointmentNotFoundException.class, () -> managementService.completeAppointment(APPOINTMENT_ID));
  }

  @Test
  void testListAppointments() {
    Appointment appointment1 = new Appointment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "", LocalDateTime.now());
    Appointment appointment2 = new Appointment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "", LocalDateTime.now());
    when(mockListAppointmentsController.handle(new ListAppointmentsRequest()))
        .thenReturn(new ListAppointmentsResponse(List.of(appointment1, appointment2)));

    List<Appointment> returnedAppointments = managementService.listAppointments();

    assertEquals(2, returnedAppointments.size());
    assertTrue(returnedAppointments.containsAll(List.of(appointment1, appointment2)));
  }
}
