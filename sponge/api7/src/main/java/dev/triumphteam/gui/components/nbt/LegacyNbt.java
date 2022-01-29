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
package dev.triumphteam.gui.components.nbt;

import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.item.inventory.ItemStack;

/**
 * Class to set / get NBT tags from items.
 * I hate this class.
 */
public final class LegacyNbt implements NbtWrapper {

    /**
     * Sets an NBT tag to the an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be set.
     * @param key       The NBT key to use.
     * @param value     The tag value to set.
     * @return An {@link ItemStack} that has NBT set.
     */
    @Override
    public ItemStack setString(@NotNull final ItemStack itemStack, final String key, final String value) {
        if (itemStack.isEmpty()) return itemStack;

        Object nmsItemStack = asNMSCopy(itemStack);
        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        setString(itemCompound, key, value);
        setTag(nmsItemStack, itemCompound);

        return asBukkitCopy(nmsItemStack);
    }

    /**
     * Removes a tag from an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be remove.
     * @param key       The NBT key to remove.
     * @return An {@link ItemStack} that has the tag removed.
     */
    @Override
    public ItemStack removeTag(@NotNull final ItemStack itemStack, final String key) {
        if (itemStack.isEmpty()) return itemStack;

        Object nmsItemStack = asNMSCopy(itemStack);
        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        remove(itemCompound, key);
        setTag(nmsItemStack, itemCompound);

        return asBukkitCopy(nmsItemStack);
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
    @Override
    public ItemStack setBoolean(@NotNull final ItemStack itemStack, final String key, final boolean value) {
        if (itemStack.isEmpty()) return itemStack;

        Object nmsItemStack = asNMSCopy(itemStack);
        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        setBoolean(itemCompound, key, value);
        setTag(nmsItemStack, itemCompound);

        return asBukkitCopy(nmsItemStack);
    }

    /**
     * Gets the NBT tag based on a given key.
     *
     * @param itemStack The {@link ItemStack} to get from.
     * @param key       The key to look for.
     * @return The tag that was stored in the {@link ItemStack}.
     */
    @Nullable
    @Override
    public String getString(@NotNull final ItemStack itemStack, final String key) {
        if (itemStack.isEmpty()) return null;

        Object nmsItemStack = asNMSCopy(itemStack);
        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        return getString(itemCompound, key);
    }

    /**
     * Mimics the itemCompound#setString method.
     *
     * @param itemCompound The ItemCompound.
     * @param key          The key to add.
     * @param value        The value to add.
     */
    private static void setString(final Object itemCompound, final String key, final String value) {
        ((NBTTagCompound) itemCompound).setString(key, value);
    }

    private static void setBoolean(final Object itemCompound, final String key, final boolean value) {
        ((NBTTagCompound) itemCompound).setBoolean(key, value);
    }

    /**
     * Mimics the itemCompound#remove method.
     *
     * @param itemCompound The ItemCompound.
     * @param key          The key to remove.
     */
    private static void remove(final Object itemCompound, final String key) {
        ((NBTTagCompound) itemCompound).removeTag(key);
    }

    /**
     * Mimics the itemCompound#getString method.
     *
     * @param itemCompound The ItemCompound.
     * @param key          The key to get from.
     * @return A string with the value from the key.
     */
    private static String getString(final Object itemCompound, final String key) {
        return ((NBTTagCompound) itemCompound).getString(key);
    }

    /**
     * Mimics the nmsItemStack#hasTag method.
     *
     * @param nmsItemStack the NMS ItemStack to check from.
     * @return True or false depending if it has tag or not.
     */
    private static boolean hasTag(final Object nmsItemStack) {
        return ((net.minecraft.item.ItemStack) nmsItemStack).hasTagCompound();
    }

    /**
     * Mimics the nmsItemStack#getTag method.
     *
     * @param nmsItemStack The NMS ItemStack to get from.
     * @return The tag compound.
     */
    public static Object getTag(final Object nmsItemStack) {
        return ((net.minecraft.item.ItemStack) nmsItemStack).getTagCompound();
    }

    /**
     * Mimics the nmsItemStack#setTag method.
     *
     * @param nmsItemStack the NMS ItemStack to set the tag to.
     * @param itemCompound The item compound to set.
     */
    private static void setTag(final Object nmsItemStack, final Object itemCompound) {
        ((net.minecraft.item.ItemStack) nmsItemStack).setTagCompound((NBTTagCompound) itemCompound);
    }

    /**
     * Mimics the new NBTTagCompound instantiation.
     *
     * @return The new NBTTagCompound.
     */
    private static Object newNBTTagCompound() {
        return new NBTTagCompound();
    }

    public static ItemStack asBukkitCopy(Object nmsItemStack) {
       return ItemStack.builder().from((ItemStack) nmsItemStack).build();
    }

    /**
     * Mimics the CraftItemStack#asNMSCopy method.
     *
     * @param itemStack The ItemStack to make NMS copy.
     * @return An NMS copy of the ItemStack.
     */
    public static Object asNMSCopy(final ItemStack itemStack) {
        net.minecraft.item.ItemStack stack = ((net.minecraft.item.ItemStack)(Object) itemStack).copy();
        stack.setCount(itemStack.getQuantity());
        return stack;
    }
}