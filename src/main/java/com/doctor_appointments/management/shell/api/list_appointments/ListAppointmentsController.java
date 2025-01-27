package main.java.com.doctor_appointments.management.shell.api.list_appointments;

import java.util.List;
import main.java.com.doctor_appointments.booking.shared.Appointment;
import main.java.com.doctor_appointments.management.core.services.IManagementService;

public class ListAppointmentsController {
  private final IManagementService managementService;
  ListAppointmentsController(IManagementService managementService) {
    this.managementService = managementService;
  }
  public ListAppointmentsResponse handle(ListAppointmentsRequest request) {
    List <Appointment> appointments =  managementService.listAppointments();
    return new ListAppointmentsResponse(appointments);
  }
}
