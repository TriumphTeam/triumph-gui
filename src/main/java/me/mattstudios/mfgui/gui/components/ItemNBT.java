package me.mattstudios.mfgui.gui.components;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Class to set / get NBT tags from items
 */
public final class ItemNBT {

    /**
     * Sets an NBT tag to the an ItemStack
     *
     * @param itemStack The current ItemStack to be set
     * @param key       The NBT key to use
     * @param value     The tag value to set
     * @return An ItemStack that has NBT set
     */
    public static ItemStack setNBTTag(final ItemStack itemStack, final String key, final String value) {
        Object nmsItemStack = asNMSCopy(itemStack);

        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        setString(itemCompound, key, value);
        setTag(nmsItemStack, itemCompound);

        return asBukkitCopy(nmsItemStack);
    }

    /**
     * Gets the NBT tag based on a given key
     *
     * @param itemStack The ItemStack to get from
     * @param key       The key to look for
     * @return The tag that was stored in the ItemStack
     */
    public static String getNBTTag(final ItemStack itemStack, final String key) {
        Object nmsItemStack = asNMSCopy(itemStack);
        Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        return getString(itemCompound, key);
    }

    /**
     * Mimics the itemCompound#setString method
     *
     * @param itemCompound The ItemCompound
     * @param key          The key to add
     * @param value        The value to add
     */
    private static void setString(final Object itemCompound, final String key, final String value) {
        try {
            Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("setString", String.class, String.class).invoke(itemCompound, key, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    /**
     * Mimics the itemCompound#getString method
     *
     * @param itemCompound The ItemCompound
     * @param key          The key to get from
     * @return A string with the value from the key
     */
    private static String getString(final Object itemCompound, final String key) {
        try {
            return (String) Objects.requireNonNull(getNMSClass("NBTTagCompound")).getMethod("getString", String.class).invoke(itemCompound, key);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the nmsItemStack#hasTag method
     *
     * @param nmsItemStack the NMS ItemStack to check from
     * @return True or false depending if it has tag or not
     */
    private static boolean hasTag(final Object nmsItemStack) {
        try {
            return (boolean) Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("hasTag").invoke(nmsItemStack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    /**
     * Mimics the nmsItemStack#getTag method
     *
     * @param nmsItemStack The NMS ItemStack to get from
     * @return The tag compound
     */
    private static Object getTag(final Object nmsItemStack) {
        try {
            return Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("getTag").invoke(nmsItemStack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the nmsItemStack#setTag method
     *
     * @param nmsItemStack the NMS ItemStack to set the tag to
     * @param itemCompound The item compound to set
     */
    private static void setTag(final Object nmsItemStack, final Object itemCompound) {
        try {
            Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("setTag", getNMSClass("NBTTagCompound")).invoke(nmsItemStack, itemCompound);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    /**
     * Mimics the new NBTTagCompound instantiation
     *
     * @return The new NBTTagCompound
     */
    private static Object newNBTTagCompound() {
        try {
            return Objects.requireNonNull(getNMSClass("NBTTagCompound")).getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the CraftItemStack#asNMSCopy method
     *
     * @param itemStack The ItemStack to make NMS copy
     * @return An NMS copy of the ItemStack
     */
    private static Object asNMSCopy(final ItemStack itemStack) {
        try {
            return Objects.requireNonNull(getCraftItemStackClass()).getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Mimics the CraftItemStack#asBukkitCopy method
     *
     * @param nmsItemStack the NMS ItemStack to turn into ItemStack
     * @return The new ItemStack
     */
    private static ItemStack asBukkitCopy(final Object nmsItemStack) {
        try {
            return (ItemStack) Objects.requireNonNull(getCraftItemStackClass()).getMethod("asBukkitCopy", getNMSClass("ItemStack")).invoke(null, nmsItemStack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Gets the server version.
     *
     * @return The string with the server version.
     */
    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
    }

    /**
     * Gets the NMS class from class name.
     *
     * @return The NMS class.
     */
    private static Class<?> getNMSClass(final String className) {
        try {
            return Class.forName("net.minecraft.server." + getServerVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets the NMS craft ItemStack class from class name.
     *
     * @return The NMS craft ItemStack class.
     */
    private static Class<?> getCraftItemStackClass() {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".inventory.CraftItemStack");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
