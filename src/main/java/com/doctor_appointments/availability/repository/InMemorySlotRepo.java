package main.java.com.doctor_appointments.availability.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import main.java.com.doctor_appointments.availability.model.SlotEntity;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyExistsException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotAlreadyReservedException;
import main.java.com.doctor_appointments.availability.shared.exceptions.SlotNotFoundException;

public class InMemorySlotRepo implements ISlotRepo {
    private final Map<UUID, SlotEntity> slots;

    public InMemorySlotRepo() {
        slots = new HashMap<>();
    }

    @Override
    public synchronized void addSlot(SlotEntity slot) throws SlotAlreadyExistsException {
        if (slots.containsKey(slot.slotId())) {
            throw new SlotAlreadyExistsException(String.format("Slot %s already exists", slot.slotId()));
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
        if (slot != null && slot.isReserved()) {
            slots.put(slotId, SlotEntity.withReserved(slot, /*isReserved=*/ false));
        }
    }

    @Override
    public synchronized void reserveSlot(UUID slotId) throws SlotAlreadyReservedException, SlotNotFoundException {
        SlotEntity slot = slots.get(slotId);
        if (slot == null) {
            throw new SlotNotFoundException(String.format("Slot %s is not found",  slotId));
        }
        if (slot.isReserved()) {
            throw new SlotAlreadyReservedException(String.format("Slot %s is already reserved", slotId));
        }
        slots.put(slotId, SlotEntity.withReserved(slot, /*isReserved=*/ true));
    }
}
