package dev.triumphteam.gui.layout;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class BoxGuiLayout implements GuiLayout {

    private final List<Slot> slots = new ArrayList<>();

    public BoxGuiLayout(final int minRow, final int minCol, final int maxRow, final int maxCol) {
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                slots.add(new Slot(Slot.fromRowCol(row, col)));
            }
        }
    }

    @Override
    public @NotNull List<@NotNull Slot> generatePositions() {
        return slots;
    }
}
