package dev.triumphteam.gui.slot;

public record Slot(int slot) {

    public int asRealSlot() {
        return slot;
    }
}
