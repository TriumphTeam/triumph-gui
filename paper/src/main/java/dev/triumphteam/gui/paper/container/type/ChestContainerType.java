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
package dev.triumphteam.gui.paper.container.type;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.slot.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class ChestContainerType implements PaperContainerType {

    private static final int LOWER_LIMIT = 0;
    private final int rows;
    private final int upperLimit;

    public ChestContainerType(final int rows) {
        this.rows = rows;
        this.upperLimit = rows * 9;
    }

    public static @NotNull GuiContainerType of(final int rows) {
        return new ChestContainerType(rows);
    }

    @Override
    public int mapSlot(final @NotNull Slot slot) {
        final var realSlot = (slot.column() + (slot.row() - 1) * 9) - 1;

        if (realSlot < LOWER_LIMIT || realSlot > upperLimit) {
            throw new TriumphGuiException(
                "Invalid slot (" + slot.row() + ", " + slot.column() + "). Valid range is (1, 1) to (" + rows + ", 9)."
            );
        }

        return realSlot;
    }

    @Override
    public Slot mapSlot(final int slot) {
        return Slot.of(slot / 9 + 1, slot % 9 + 1);
    }

    @Override
    public @NotNull Inventory createInventory(
        final @NotNull InventoryHolder holder,
        final @NotNull Component title
    ) {
        return Bukkit.createInventory(holder, upperLimit, title);
    }
}
