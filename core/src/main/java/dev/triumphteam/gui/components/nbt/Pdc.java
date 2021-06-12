package dev.triumphteam.gui.components.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Wrapper for compatibility with {@link LegacyNbt}.
 * This ideally wouldn't need exist, but legacy.
 */
public final class Pdc implements NbtWrapper {

    /**
     * Plugin instance required for the {@link NamespacedKey}.
     */
    private static final Plugin PLUGIN = JavaPlugin.getProvidingPlugin(Pdc.class);

    /**
     * Sets an String NBT tag to the an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be set.
     * @param key       The NBT key to use.
     * @param value     The tag value to set.
     * @return An {@link ItemStack} that has NBT set.
     */
    @Override
    public ItemStack setString(@NotNull final ItemStack itemStack, final String key, final String value) {
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
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
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.getPersistentDataContainer().remove(new NamespacedKey(PLUGIN, key));
        itemStack.setItemMeta(meta);
        return itemStack;
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
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.BYTE, value ? (byte) 1 : 0);
        itemStack.setItemMeta(meta);
        return itemStack;
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
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING);
    }

}
