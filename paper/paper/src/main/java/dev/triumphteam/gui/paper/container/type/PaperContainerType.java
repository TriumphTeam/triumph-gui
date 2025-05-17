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
import dev.triumphteam.gui.paper.nms.v1_21.inventory.PaperGuiInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface PaperContainerType extends GuiContainerType {

    static @NotNull PaperContainerType chest(final int rows) {
        return new ChestContainerType(rows);
    }

    static @NotNull PaperContainerType hopper() {
        return new HopperContainerType();
    }

    static @NotNull PaperContainerType anvil(final @NotNull Consumer<String> inputHandler) {
        return new AnvilContainerType(inputHandler);
    }

    @NotNull
    PaperGuiInventory createInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull Component title,
            final @NotNull Player player,
            final boolean usePlayerInventory
    );

    @Override
    @NotNull PaperContainerType copy();
}
