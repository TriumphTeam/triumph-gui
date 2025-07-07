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

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface GuiContainer {

    @NotNull Component title();

    void title(final @NotNull Component title);

    @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder);

    @NotNull GuiType guiType();

    int inventorySize();

    int rows();

    class Chest implements GuiContainer {

        private final InventoryProvider.Chest inventoryProvider;

        private int rows;
        private Component title;

        public Chest(
            final @NotNull Component title,
            final @NotNull InventoryProvider.Chest inventoryProvider,
            final int rows
        ) {
            this.inventoryProvider = inventoryProvider;
            this.title = title;
            this.rows = rows;
        }

        @Override
        public @NotNull Component title() {
            return title;
        }

        @Override
        public void title(final @NotNull Component title) {
            this.title = title;
        }

        @Override
        public int inventorySize() {
            return rows * 9;
        }

        @Override
        public @NotNull GuiType guiType() {
            return GuiType.CHEST;
        }

        @Override
        public int rows() {
            return rows;
        }

        public void rows(final int rows) {
            this.rows = rows;
        }

        @Override
        public @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, inventorySize());
        }
    }

    class Typed implements GuiContainer {

        private final InventoryProvider.Typed inventoryProvider;
        private final GuiType guiType;
        private Component title;

        public Typed(
            final @NotNull Component title,
            final @NotNull InventoryProvider.Typed inventoryProvider,
            final @NotNull GuiType guiType
        ) {
            this.inventoryProvider = inventoryProvider;
            this.title = title;
            this.guiType = guiType;
        }

        @Override
        public @NotNull Component title() {
            return title;
        }

        @Override
        public void title(@NotNull Component title) {
            this.title = title;
        }

        @Override
        public int inventorySize() {
            return guiType.getLimit();
        }

        @Override
        public @NotNull GuiType guiType() {
            return guiType;
        }

        @Override
        public int rows() {
            return 1;
        }

        @Override
        public @NotNull Inventory createInventory(@NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, guiType.getInventoryType());
        }
    }
}
