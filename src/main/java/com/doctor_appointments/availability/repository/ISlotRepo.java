package main.java.com.doctor_appointments.availability.repository;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyExistsException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReleasedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;

public interface ISlotRepo {
    void addSlot(SlotEntity slot) throws SlotAlreadyExistsException;

    List<SlotEntity> listSlots();

    void releaseSlot(UUID slotId) throws SlotAlreadyReleasedException, SlotNotFoundException;

    void reserveSlot(UUID slotId) throws SlotAlreadyReservedException, SlotNotFoundException;
}
