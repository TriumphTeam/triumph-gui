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
