package dev.triumphteam.gui.container.type.types;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractChestContainerType implements GuiContainerType {

    private static final int LOWER_LIMIT = 0;
    private final int rows;
    private final int upperLimit;

    public AbstractChestContainerType(final int rows) {
        this.rows = rows;
        this.upperLimit = rows * GuiContainerType.COLUMNS;
    }

    // TODO(important): VALIDATION NEEDS TO BE RE-ADDED

    @Override
    public int toSlot(final int row, final int column) {
        return GuiContainerType.defaultMapping(row, column);
    }

    @Override
    public @NotNull Slot toSlot(final int slot) {
        return Slot.of(slot / GuiContainerType.COLUMNS + 1, slot % GuiContainerType.COLUMNS + 1);
    }

    @Override
    public int toTopInventory(final int slot) {
        return slot; // The normal slots are the same for chest inventories.
    }

    @Override
    public int toPlayerInventory(final int slot) {
        return GuiContainerType.defaultPlayerInventoryMapping(slot - upperLimit);
    }

    @Override
    public boolean isPlayerInventory(final int slot) {
        return slot > upperLimit - 1;
    }

    protected int getUpperLimit() {
        return upperLimit;
    }
}
