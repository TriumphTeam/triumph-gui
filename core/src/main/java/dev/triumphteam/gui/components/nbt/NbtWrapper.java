package dev.triumphteam.gui.components.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NbtWrapper {

    /**
     * Sets an String NBT tag to the an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be set.
     * @param key       The NBT key to use.
     * @param value     The tag value to set.
     * @return An {@link ItemStack} that has NBT set.
     */
    ItemStack setString(@NotNull final ItemStack itemStack, final String key, final String value);

    /**
     * Removes a tag from an {@link ItemStack}.
     *
     * @param itemStack The current {@link ItemStack} to be remove.
     * @param key       The NBT key to remove.
     * @return An {@link ItemStack} that has the tag removed.
     */
    ItemStack removeTag(@NotNull final ItemStack itemStack, final String key);

    /**
     * Sets a boolean to the {@link ItemStack}.
     * Mainly used for setting an item to be unbreakable on older versions.
     *
     * @param itemStack The {@link ItemStack} to set the boolean to.
     * @param key       The key to use.
     * @param value     The boolean value.
     * @return An {@link ItemStack} with a boolean value set.
     */
    ItemStack setBoolean(@NotNull final ItemStack itemStack, final String key, final boolean value);

    /**
     * Gets the NBT tag based on a given key.
     *
     * @param itemStack The {@link ItemStack} to get from.
     * @param key       The key to look for.
     * @return The tag that was stored in the {@link ItemStack}.
     */
    @Nullable
    String getString(@NotNull final ItemStack itemStack, final String key);

}
