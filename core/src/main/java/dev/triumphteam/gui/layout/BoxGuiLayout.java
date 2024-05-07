package dev.triumphteam.gui.layout;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class BoxGuiLayout implements GuiLayout {

    private final List<Slot> slots = new ArrayList<>();

    public BoxGuiLayout(final @NotNull Slot min, final @NotNull Slot max) {
        for (int row = min.row(); row <= max.row(); row++) {
            for (int col = min.column(); col <= max.column(); col++) {
                slots.add(Slot.of(row, col));
            }
        }
    }

    @Override
    public @NotNull List<@NotNull Slot> generatePositions() {
        return slots;
    }
}
