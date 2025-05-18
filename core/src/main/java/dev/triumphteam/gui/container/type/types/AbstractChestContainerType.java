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

    protected int getRows() {
        return rows;
    }
}
