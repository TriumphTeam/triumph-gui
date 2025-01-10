/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.components.InventoryProvider;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.guis.BaseGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class BaseChestGuiBuilder<G extends BaseGui, B extends BaseChestGuiBuilder<G, B>> extends BaseGuiBuilder<G, B> {

    private int rows = 1;
    private InventoryProvider.Chest inventoryProvider =
            (title, ownder, rows) -> Bukkit.createInventory(ownder, rows, Legacy.SERIALIZER.serialize(title));

    /**
     * Sets the rows for the GUI
     * This will only work on CHEST {@link dev.triumphteam.gui.components.GuiType}
     *
     * @param rows The number of rows
     * @return The builder
     */
    @NotNull
    @Contract("_ -> this")
    public B rows(final int rows) {
        this.rows = rows;
        return (B) this;
    }

    public B inventory(@NotNull final InventoryProvider.Chest inventoryProvider) {
        this.inventoryProvider = inventoryProvider;
        return (B) this;
    }

    /**
     * Getter for the rows
     *
     * @return The amount of rows
     */
    protected int getRows() {
        return rows;
    }
}
