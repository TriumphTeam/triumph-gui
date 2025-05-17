package dev.triumphteam.gui.container.type.types;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class AbstractAnvilContainerType implements GuiContainerType {

    public static final int ANVIL_ROW = 5;
    private static final int ANVIL_SLOTS = 3;

    private String input = "";
    private final Consumer<String> inputHandler;

    public AbstractAnvilContainerType(final @NotNull Consumer<String> inputHandler) {
        this.inputHandler = inputHandler;
    }

    // TODO(important): VALIDATION NEEDS TO BE RE-ADDED

    @Override
    public int toSlot(final int row, final int column) {
        if (row == ANVIL_ROW) return column - 1;
        return GuiContainerType.defaultMapping(row, column) + ANVIL_SLOTS;
    }

    @Override
    public @NotNull Slot toSlot(final int slot) {
        // TODO
        return Slot.of(slot / GuiContainerType.COLUMNS + 1, slot % GuiContainerType.COLUMNS + 1);
    }

    @Override
    public int toTopInventory(final int slot) {
        return slot; // No need to transform anything.
    }

    @Override
    public int toPlayerInventory(final int slot) {
        return GuiContainerType.defaultPlayerInventoryMapping(slot - ANVIL_SLOTS);
    }

    @Override
    public boolean isPlayerInventory(final int slot) {
        return slot >= ANVIL_SLOTS;
    }

    public void setInput(final @NotNull String input) {
        if (this.input.equals(input)) return;
        inputHandler.accept(input);
    }

    protected Consumer<String> getInputHandler() {
        return inputHandler;
    }
}
