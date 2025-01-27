package main.java.com.doctor_appointments.booking.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import main.java.com.doctor_appointments.booking.domain.AppointmentEntity;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import org.junit.jupiter.api.Test;

public class AppointmentDtoTest {
  @Test
  void testFromRepo() {
    UUID appointmentId = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservedAt = LocalDateTime.now();
    AppointmentEntity appointmentEntity = new AppointmentEntity(appointmentId, slotId, patientId, patientName, reservedAt);
    AppointmentDto expectedAppointmentDto = new AppointmentDto(appointmentId, slotId, patientId, patientName, reservedAt);

    AppointmentDto convertedDto = AppointmentDto.fromRepo(appointmentEntity);

    assertEquals(expectedAppointmentDto, convertedDto);
  }

  @Test
  void testToShared() {
    UUID appointmentId = UUID.randomUUID();
    UUID slotId = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    String patientName = "some_patient";
    LocalDateTime reservedAt = LocalDateTime.now();
    AppointmentDto appointmentDto = new AppointmentDto(appointmentId, slotId, patientId, patientName, reservedAt);
    Appointment expectedAppointment = new Appointment(appointmentId, slotId, patientId, patientName, reservedAt);

    Appointment convertedAppointment = appointmentDto.toSharedAppointment();

    assertEquals(expectedAppointment, convertedAppointment);
  }
}
