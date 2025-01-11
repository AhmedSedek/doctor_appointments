package main.java.com.doctor_appointments.availability.api.list_available_slots;

import main.java.com.doctor_appointments.availability.business.SlotDto;

import java.util.List;

public record ListAvailableSlotsResponse(List<SlotDto> slots) { }
