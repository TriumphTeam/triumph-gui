/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.container.type.types;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class AbstractAnvilContainerType implements GuiContainerType {

    public static final int ANVIL_ROW = 5;
    public static final int RESULT_SLOT = 2;
    private static final int ANVIL_SLOTS = 3;
    
    private final Consumer<String> inputHandler;
    private String input = "";

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
        this.input = input;
        inputHandler.accept(input);
    }

    protected Consumer<String> getInputHandler() {
        return inputHandler;
    }
}
