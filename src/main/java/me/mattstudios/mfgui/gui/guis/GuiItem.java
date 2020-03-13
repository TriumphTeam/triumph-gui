package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.mattstudios.mfgui.gui.components.ItemNBT.setNBTTag;

public final class GuiItem {

    // Action to do when clicking on the item
    private GuiAction<InventoryClickEvent> action;

    // The ItemStack of the GuiItem
    private ItemStack itemStack;
    // Random UUID to identify the idem when clicking
    private final UUID uuid = UUID.randomUUID();

    /**
     * Main constructor of the GuiItem
     *
     * @param itemStack The ItemStack to be used
     * @param action    The action to do when clicking on the Item
     */
    public GuiItem(final ItemStack itemStack, final GuiAction<InventoryClickEvent> action) {
        if (action == null) this.action = event -> {
        };
        else this.action = action;

        // Sets the UUID to an NBT tag to be identifiable later
        this.itemStack = setNBTTag(itemStack, "mf-gui", uuid.toString());
    }

    /**
     * Secondary constructor with no action
     *
     * @param itemStack The ItemStack to be used
     */
    public GuiItem(final ItemStack itemStack) {
        this(itemStack, null);
    }

    /**
     * Replaces the ItemStack of the GUI Item
     *
     * @param itemStack The new ItemStack
     */
    public void setItemStack(final ItemStack itemStack) {
        this.itemStack = setNBTTag(itemStack, "mf-gui", uuid.toString());
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
