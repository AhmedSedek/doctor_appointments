package main.java.com.doctor_appointments.availability.api.list_slots;

import main.java.com.doctor_appointments.availability.business.SlotDto;

import java.util.List;

public record ListSlotsResponse(List<SlotDto> slots) {
}
