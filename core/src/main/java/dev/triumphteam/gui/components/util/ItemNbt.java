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

import dev.triumphteam.gui.components.nbt.LegacyNbt;
import dev.triumphteam.gui.components.nbt.NbtWrapper;
import dev.triumphteam.gui.components.nbt.Pdc;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Ideally this wouldn't need to be an util, but because of the {@link LegacyNbt} it makes it easier. Legacy..
 * Will use the PDC wrapper if version is higher than 1.14
 */
public final class ItemNbt {

    private static final NbtWrapper nbt = selectNbt();

    /**
     * Sets an NBT tag to the an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be set.
     * @param key       The NBT key to use.
     * @param value     The tag value to set.
     * @return An {@link ItemStack} that has NBT set.
     */
    public static ItemStack setString(@NotNull final ItemStack itemStack, @NotNull final String key, @NotNull final String value) {
        return nbt.setString(itemStack, key, value);
    }

    /**
     * Gets the NBT tag based on a given key.
     *
     * @param itemStack The {@link ItemStack} to get from.
     * @param key       The key to look for.
     * @return The tag that was stored in the {@link ItemStack}.
     */
    public static String getString(@NotNull final ItemStack itemStack, @NotNull final String key) {
        return nbt.getString(itemStack, key);
    }

    /**
     * Sets a boolean to the {@link ItemStack}.
     * Mainly used for setting an item to be unbreakable on older versions.
     *
     * @param itemStack The {@link ItemStack} to set the boolean to.
     * @param key       The key to use.
     * @param value     The boolean value.
     * @return An {@link ItemStack} with a boolean value set.
     */
    public static ItemStack setBoolean(@NotNull final ItemStack itemStack, @NotNull final String key, final boolean value) {
        return nbt.setBoolean(itemStack, key, value);
    }

    /**
     * Removes a tag from an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be remove.
     * @param key       The NBT key to remove.
     * @return An {@link ItemStack} that has the tag removed.
     */
    public static ItemStack removeTag(@NotNull final ItemStack itemStack, @NotNull final String key) {
        return nbt.removeTag(itemStack, key);
    }

    /**
     * Selects which {@link NbtWrapper} to use based on server version.
     *
     * @return A {@link NbtWrapper} implementation, {@link Pdc} if version is higher than 1.14 and {@link LegacyNbt} if not.
     */
    private static NbtWrapper selectNbt() {
        if (VersionHelper.IS_PDC_VERSION) return new Pdc();
        return new LegacyNbt();
    }

}
