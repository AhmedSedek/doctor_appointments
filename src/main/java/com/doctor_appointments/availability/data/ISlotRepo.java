package main.java.com.doctor_appointments.availability.data;

import java.util.List;

public interface ISlotRepo {
    void addSlot(SlotEntity slot);

    List<SlotEntity> listSlots();
}
