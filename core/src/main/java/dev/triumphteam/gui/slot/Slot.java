package dev.triumphteam.gui.slot;

public record Slot(int row, int column) {

    public static Slot of(final int row, final int column) {
        return new Slot(row, column);
    }
}
