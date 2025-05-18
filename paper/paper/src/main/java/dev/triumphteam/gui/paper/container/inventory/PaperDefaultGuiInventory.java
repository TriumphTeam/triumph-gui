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
package dev.triumphteam.gui.paper.container.inventory;

import dev.triumphteam.gui.paper.PaperGuiInventory;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * By default, all inventories are just plain paper api inventories.
 * We only dive into NMS territory for combined inventories and certain unsupported like Anvil.
 */
public final class PaperDefaultGuiInventory implements PaperGuiInventory {

    private final Player player;
    private final PaperContainerType containerType;
    private final Inventory inventory;
    private final Inventory playerInventory;

    public PaperDefaultGuiInventory(
            final @NotNull Player player,
            final @NotNull PaperContainerType containerType,
            final @NotNull Inventory inventory
    ) {
        this.player = player;
        this.containerType = containerType;
        this.inventory = inventory;
        this.playerInventory = player.getInventory();
    }

    @Override
    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getBukkitInventory() {
        return inventory;
    }

    @Override
    public void setTopInventoryItem(final int slot, final @NotNull ItemStack itemStack) {
        inventory.setItem(containerType.toTopInventory(slot), itemStack);
    }

    @Override
    public void setPlayerInventoryItem(final int slot, final @NotNull ItemStack itemStack) {
        playerInventory.setItem(containerType.toPlayerInventory(slot), itemStack);
    }

    @Override
    public void clearTopInventorySlot(final int slot) {
        inventory.clear(containerType.toTopInventory(slot));
    }

    @Override
    public void clearPlayerInventorySlot(final int slot) {
        playerInventory.clear(containerType.toPlayerInventory(slot));
    }
}
