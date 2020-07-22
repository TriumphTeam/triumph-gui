package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiAction;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class GuiItem {

    // Action to do when clicking on the item
    private GuiAction<InventoryClickEvent> action;

    // The ItemStack of the GuiItem
    private ItemStack itemStack;

    // Random UUID to identify the item when clicking
    private final UUID uuid = UUID.randomUUID();

    /**
     * Main constructor of the GuiItem
     *
     * @param itemStack The ItemStack to be used
     * @param action    The action to do when clicking on the Item
     */
    public GuiItem(@NotNull final ItemStack itemStack, final GuiAction<InventoryClickEvent> action) {
        Validate.notNull(itemStack, "The ItemStack for the GUI Item cannot be null!");

        if (action == null) this.action = event -> {
        };
        else this.action = action;

        // Sets the UUID to an NBT tag to be identifiable later
        this.itemStack = ItemNBT.setNBTTag(itemStack, "mf-gui", uuid.toString());
    }

    /**
     * Secondary constructor with no action
     *
     * @param itemStack The ItemStack to be used
     */
    public GuiItem(@NotNull final ItemStack itemStack) {
        this(itemStack, null);
    }

    /**
     * Alternate constructor that takes {@code Material}
     * instead of an {@code ItemStack} with no {@code GuiAction}.
     *
     * @param material The {@code Material} to be used when invoking class.
     */
    public GuiItem(@NotNull final Material material) {
        this(new ItemStack(material), null);
    }

    /**
     * Alternate constructor that takes {@code Material}
     * instead of an {@code ItemStack} which takes a {@code GuiAction}.
     *
     * @param material The {@code Material} to be used when invoking class.
     * @param action   The action representing a {@code Consumer} that
     *                 should be passed on {@code InventoryClickEvent}.
     */
    public GuiItem(@NotNull final Material material, @Nullable final GuiAction<InventoryClickEvent> action) {
        this(new ItemStack(material), action);
    }

    /**
     * Replaces the ItemStack of the GUI Item
     *
     * @param itemStack The new ItemStack
     */
    public void setItemStack(@NotNull final ItemStack itemStack) {
        Validate.notNull(itemStack, "The itemstack for the GUI Item cannot be null!");
        this.itemStack = ItemNBT.setNBTTag(itemStack, "mf-gui", uuid.toString());
    }

    /**
     * Replaces the action of the current GUI Item
     *
     * @param action The new action to set
     */
    public void setAction(final GuiAction<InventoryClickEvent> action) {
        this.action = action;
    }

    /**
     * Gets the GuiItem's ItemStack
     *
     * @return The ItemStack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the random UUID that was generated when the GuiItem was made
     */
    UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the action to do when the player clicks on it
     */
    GuiAction<InventoryClickEvent> getAction() {
        return action;
    }

}
