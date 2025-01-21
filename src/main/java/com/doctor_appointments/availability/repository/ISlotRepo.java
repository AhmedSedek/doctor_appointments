package main.java.com.doctor_appointments.availability.repository;

import java.util.List;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;

public interface ISlotRepo {
    void addSlot(SlotEntity slot);

    List<SlotEntity> listSlots();

    void releaseSlot(UUID slotId);
    void reserveSlot(UUID slotId);
}
