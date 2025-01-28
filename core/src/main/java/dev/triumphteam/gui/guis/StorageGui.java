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
package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.components.GuiContainer;
import dev.triumphteam.gui.components.InteractionModifier;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * GUI that does not clear its items once it's closed
 */
@SuppressWarnings("unused")
public class StorageGui extends BaseGui {

    public StorageGui(final @NotNull GuiContainer guiContainer, @NotNull final Set<InteractionModifier> interactionModifiers) {
        super(guiContainer, interactionModifiers);
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    @NotNull
    public Map<@NotNull Integer, @NotNull ItemStack> addItem(@NotNull final ItemStack... items) {
        return Collections.unmodifiableMap(getInventory().addItem(items));
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    public Map<@NotNull Integer, @NotNull ItemStack> addItem(@NotNull final List<ItemStack> items) {
        return addItem(items.toArray(new ItemStack[0]));
    }

    /**
     * Overridden {@link BaseGui#open(HumanEntity)} to prevent
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        if (player.isSleeping()) return;
        populateGui();
        player.openInventory(getInventory());
    }
}
