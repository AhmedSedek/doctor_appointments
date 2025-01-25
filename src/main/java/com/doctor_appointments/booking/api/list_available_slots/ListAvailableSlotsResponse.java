package main.java.com.doctor_appointments.booking.api.list_available_slots;

import java.util.List;
import main.java.com.doctor_appointments.availability.shared.Slot;

public record ListAvailableSlotsResponse(List<Slot> slots) {

}
