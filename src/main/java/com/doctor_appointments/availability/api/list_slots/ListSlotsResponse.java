package main.java.com.doctor_appointments.availability.api.list_slots;

import java.util.List;
import main.java.com.doctor_appointments.availability.shared.Slot;

public record ListSlotsResponse(List<Slot> slots) {
}
