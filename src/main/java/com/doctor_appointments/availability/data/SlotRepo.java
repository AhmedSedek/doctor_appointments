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

    @Override
    public synchronized void releaseSlot(UUID slotId) {
        SlotEntity slot = slots.get(slotId);
        if (slot == null) {
            throw new RuntimeException("No slot found with ID: " + slotId);
        }
        if (!slot.isReserved()) {
            System.out.println(String.format("Slot %s is already released", slotId));
        } else {
            slots.put(slotId, SlotEntity.withReserved(slot, /*isReserved=*/ false));
        }
    }

    @Override
    public synchronized void reserveSlot(UUID slotId) {
        SlotEntity slot = slots.get(slotId);
        if (slot == null) {
            throw new RuntimeException("No slot found with ID: " + slotId);
        }
        if (slot.isReserved()) {
            throw new IllegalStateException("Slot already reserved: " + slotId);
        }
        slots.put(slotId, SlotEntity.withReserved(slot, /*isReserved=*/ true));
    }
}
