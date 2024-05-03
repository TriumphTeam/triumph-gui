package dev.triumphteam.gui.slot;

public record Slot(int slot) {

    public static int fromRowCol(final int row, final int column) {
        return (column + (row - 1) * 9) - 1;
    }
}
