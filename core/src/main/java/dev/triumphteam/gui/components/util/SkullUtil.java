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
package dev.triumphteam.gui.components.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class SkullUtil {

    /**
     * The main SKULL material for the version
     */
    private static final Material SKULL = getSkullMaterial();

    /**
     * Gets the correct {@link Material} for the version
     *
     * @return The correct SKULL {@link Material}
     */
    private static Material getSkullMaterial() {
        if (VersionHelper.IS_ITEM_LEGACY) {
            return Material.valueOf("SKULL_ITEM");
        }

        return Material.PLAYER_HEAD;
    }

    /**
     * Create a player skull
     *
     * @return player skull
     */
    @SuppressWarnings("deprecation")
    public static ItemStack skull() {
        return VersionHelper.IS_ITEM_LEGACY ? new ItemStack(SKULL, 1, (short) 3) : new ItemStack(SKULL);
    }

    /**
     * Checks if an {@link ItemStack} is a player skull
     *
     * @param item item to check
     * @return whether the item is a player skull
     */
    @SuppressWarnings("deprecation")
    public static boolean isPlayerSkull(@NotNull final ItemStack item) {
        if (VersionHelper.IS_ITEM_LEGACY) {
            return item.getType() == SKULL && item.getDurability() == (short) 3;
        }

        return item.getType() == SKULL;
    }

}
