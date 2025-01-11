package main.java.com.doctor_appointments.availability.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SlotRepo implements ISlotRepo {
    private final Map<UUID, SlotEntity> slots;

    SlotRepo() {
        slots = new HashMap<>();
    }

    @Override
    public synchronized void addSlot(SlotEntity slot) {
        if (slots.containsKey(slot.slotId())) {
            throw new IllegalArgumentException("Conflict UUID: " + slot.slotId());
        }
        slots.put(slot.slotId(), slot);
    }

    @Override
    public List<SlotEntity> listSlots() {
        return slots.values().stream().toList();
    }
}
