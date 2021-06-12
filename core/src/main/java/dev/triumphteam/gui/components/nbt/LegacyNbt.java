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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Class to set / get NBT tags from items.
 * I hate this class.
 */
public final class LegacyNbt implements NbtWrapper {

    public static final String PACKAGE_NAME = Bukkit.getServer().getClass().getPackage().getName();
    public static final String NMS_VERSION = PACKAGE_NAME.substring(PACKAGE_NAME.lastIndexOf(46) + 1);

    private static Method getStringMethod;
    private static Method setStringMethod;
    private static Method setBooleanMethod;
    private static Method hasTagMethod;
    private static Method getTagMethod;
    private static Method setTagMethod;
    private static Method removeTagMethod;
    private static Method asNMSCopyMethod;
    private static Method asBukkitCopyMethod;

    private static Constructor<?> nbtCompoundConstructor;

    static {
        try {
            getStringMethod = Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("getString", String.class);
            removeTagMethod = Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("remove", String.class);
            setStringMethod = Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("setString", String.class, String.class);
            setBooleanMethod = Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("setBoolean", String.class, boolean.class);
            hasTagMethod = Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("hasTag");
            getTagMethod = Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("getTag");
            setTagMethod = Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("setTag", getNMSClass("NBTTagCompound"));
            nbtCompoundConstructor = Objects.requireNonNull(getNMSClass("NBTTagCompound")).getDeclaredConstructor();
            asNMSCopyMethod = Objects.requireNonNull(getCraftItemStackClass()).getMethod("asNMSCopy", ItemStack.class);
            asBukkitCopyMethod = Objects.requireNonNull(getCraftItemStackClass()).getMethod("asBukkitCopy", getNMSClass("ItemStack"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

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
        if (itemStack.getType() == Material.AIR) return itemStack;

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
        if (itemStack.getType() == Material.AIR) return itemStack;

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
        if (itemStack.getType() == Material.AIR) return itemStack;

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
        if (itemStack.getType() == Material.AIR) return null;

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
        try {
            setStringMethod.invoke(itemCompound, key, value);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    private static void setBoolean(final Object itemCompound, final String key, final boolean value) {
        try {
            setBooleanMethod.invoke(itemCompound, key, value);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    /**
     * Mimics the itemCompound#remove method.
     *
     * @param itemCompound The ItemCompound.
     * @param key          The key to remove.
     */
    private static void remove(final Object itemCompound, final String key) {
        try {
            removeTagMethod.invoke(itemCompound, key);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    /**
     * Mimics the itemCompound#getString method.
     *
     * @param itemCompound The ItemCompound.
     * @param key          The key to get from.
     * @return A string with the value from the key.
     */
    private static String getString(final Object itemCompound, final String key) {
        try {
            return (String) getStringMethod.invoke(itemCompound, key);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the nmsItemStack#hasTag method.
     *
     * @param nmsItemStack the NMS ItemStack to check from.
     * @return True or false depending if it has tag or not.
     */
    private static boolean hasTag(final Object nmsItemStack) {
        try {
            return (boolean) hasTagMethod.invoke(nmsItemStack);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    /**
     * Mimics the nmsItemStack#getTag method.
     *
     * @param nmsItemStack The NMS ItemStack to get from.
     * @return The tag compound.
     */
    public static Object getTag(final Object nmsItemStack) {
        try {
            return getTagMethod.invoke(nmsItemStack);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the nmsItemStack#setTag method.
     *
     * @param nmsItemStack the NMS ItemStack to set the tag to.
     * @param itemCompound The item compound to set.
     */
    private static void setTag(final Object nmsItemStack, final Object itemCompound) {
        try {
            setTagMethod.invoke(nmsItemStack, itemCompound);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    /**
     * Mimics the new NBTTagCompound instantiation.
     *
     * @return The new NBTTagCompound.
     */
    private static Object newNBTTagCompound() {
        try {
            return nbtCompoundConstructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the CraftItemStack#asNMSCopy method.
     *
     * @param itemStack The ItemStack to make NMS copy.
     * @return An NMS copy of the ItemStack.
     */
    public static Object asNMSCopy(final ItemStack itemStack) {
        try {
            return asNMSCopyMethod.invoke(null, itemStack);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the CraftItemStack#asBukkitCopy method.
     *
     * @param nmsItemStack The NMS ItemStack to turn into {@link ItemStack}.
     * @return The new {@link ItemStack}.
     */
    public static ItemStack asBukkitCopy(final Object nmsItemStack) {
        try {
            return (ItemStack) asBukkitCopyMethod.invoke(null, nmsItemStack);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Gets the NMS class from class name.
     *
     * @return The NMS class.
     */
    private static Class<?> getNMSClass(final String className) {
        try {
            return Class.forName("net.minecraft.server." + NMS_VERSION + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets the NMS craft {@link ItemStack} class from class name.
     *
     * @return The NMS craft {@link ItemStack} class.
     */
    private static Class<?> getCraftItemStackClass() {
        try {
            return Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".inventory.CraftItemStack");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}