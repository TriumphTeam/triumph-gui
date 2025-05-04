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
    public int mapSlot(final int row, final int column) {
        return (column + (row - 1) * GuiContainerType.COLUMNS) - 1;
    }

    @Override
    public @NotNull Slot mapSlot(final int slot) {
        return Slot.of(slot / GuiContainerType.COLUMNS + 1, slot % GuiContainerType.COLUMNS + 1);
    }

    @Override
    public int mapToPlayerInventory(final int slot) {
        final var relative = slot - upperLimit;

        // If it's the hotbar area.
        if (relative >= GuiContainerType.PLAYER_INVENTORY_SIZE) {
            return relative - GuiContainerType.PLAYER_INVENTORY_SIZE; // Maps to hotbar slots 0-8.
        }

        // Otherwise, it's the main inventory.
        return relative + GuiContainerType.COLUMNS; // Maps to inventory slots 9-35.
    }

    @Override
    public boolean isPlayerInventory(final int slot) {
        return slot > upperLimit - 1;
    }

    protected int getUpperLimit() {
        return upperLimit;
    }
}
