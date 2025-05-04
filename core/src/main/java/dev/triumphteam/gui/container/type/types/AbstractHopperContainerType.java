package dev.triumphteam.gui.container.type.types;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractHopperContainerType implements GuiContainerType {

    public static final int UPPER_LIMIT = 5;
    public static final int HOPPER_ROW = 5;

    @Override
    public int toSlot(final int row, final int column) {
        // On a hopper, we want to map 5, x into x for a 0-5 slot.
        if (row >= HOPPER_ROW) return column - 1;
        // All slots after are offset by the hopper's size.
        return GuiContainerType.defaultMapping(row, column) + UPPER_LIMIT;
    }

    @Override
    public @NotNull Slot toSlot(final int slot) {
        return Slot.of(1, slot + 1);
    }

    @Override
    public int toTopInventory(final int slot) {
        return slot;
    }

    @Override
    public int toPlayerInventory(final int slot) {
        return GuiContainerType.defaultPlayerInventoryMapping(slot - UPPER_LIMIT);
    }

    @Override
    public boolean isPlayerInventory(final int slot) {
        return slot >= UPPER_LIMIT;
    }
}
