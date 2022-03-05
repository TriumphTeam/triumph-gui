/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
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
package dev.triumphteam.gui.components;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

/**
 * Possible types for a {@link Gui} - similar to {@link InventoryType}
 */
public enum GuiType {

    /**
     * Standard chest inventory
     */
    CHEST(InventoryType.CHEST, 9),
    /**
     * Standard workbench inventory
     */
    WORKBENCH(InventoryType.WORKBENCH, 9),
    /**
     * Standard hopper inventory
     */
    HOPPER(InventoryType.HOPPER, 5),
    /**
     * Standard dispenser inventory
     */
    DISPENSER(InventoryType.DISPENSER, 8),
    /**
     * Standard brewing stand inventory
     */
    BREWING(InventoryType.BREWING, 4);

    @NotNull
    private final InventoryType inventoryType;
    private final int limit;

    /**
     * Creates a new {@link GuiType}
     * @param inventoryType the {@link InventoryType}
     * @param limit the amount of slots across a chest or limits for a unique inventory type
     */
    GuiType(@NotNull final InventoryType inventoryType, final int limit) {
        this.inventoryType = inventoryType;
        this.limit = limit;
    }

    /**
     * Gets the {@link InventoryType} of this {@link GuiType}
     * @return the {@link InventoryType}
     */
    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    /**
     * Gets the amount of slots across a chest or limits for a unique inventory type
     * @return the limit of the {@link GuiType}
     */
    public int getLimit() {
        return limit;
    }

}
