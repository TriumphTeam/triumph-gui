package dev.triumphteam.gui.slot;

public record Slot(int row, int col) {

    public int asRealSlot() {
        return 0;
    }
}
