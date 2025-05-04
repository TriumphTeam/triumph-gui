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
package dev.triumphteam.gui.container.type;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public interface GuiContainerType {

    int COLUMNS = 9;
    int PLAYER_INVENTORY_SIZE = 27;
    int PLAYER_INVENTORY_ROWS = 3;
    int PLAYER_INVENTORY_HOTBAR_ROWS = 4;

    static int defaultMapping(final int row, final int column) {
        return (column + (row - 1) * GuiContainerType.COLUMNS) - 1;
    }

    static int defaultPlayerInventoryMapping(final int relative) {
        // If it's the hotbar area.
        if (relative >= GuiContainerType.PLAYER_INVENTORY_SIZE) {
            return relative - GuiContainerType.PLAYER_INVENTORY_SIZE; // Maps to hotbar slots 0-8.
        }

        // Otherwise, it's the main inventory.
        return relative + GuiContainerType.COLUMNS; // Maps to inventory slots 9-35.
    }

    int toSlot(final int row, final int column);

    @NotNull Slot toSlot(final int slot);

    int toTopInventory(final int slot);

    int toPlayerInventory(final int slot);

    boolean isPlayerInventory(final int slot);
}
