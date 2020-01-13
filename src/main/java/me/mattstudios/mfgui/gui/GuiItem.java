package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.mattstudios.mfgui.gui.components.ItemNBT.setNBTTag;

public final class GuiItem {

    // Action to do when clicking on the item
    private GuiClickResolver action;

    // The ItemStack of the GuiItem
    private final ItemStack itemStack;
    // Random UUID to identify the idem when clicking
    private final UUID uuid = UUID.randomUUID();

    /**
     * Main constructor of the GuiItem
     * @param itemStack The ItemStack to be used
     * @param action The action to do when clicking on the Item
     */
    public GuiItem(final ItemStack itemStack, final GuiClickResolver action) {
        if (action == null) this.action = event -> {};
        else this.action = action;

        // Sets the UUID to an NBT tag to be identifiable later
       this.itemStack = setNBTTag(itemStack, "mf-gui", uuid.toString());
    }

    /**
     * Secondary constructor with no action
     * @param itemStack The ItemStack to be used
     */
    public GuiItem(final ItemStack itemStack) {
        this(itemStack, null);
    }

    /**
     * Gets the GuiItem's ItemStack
     * @return the ItemStack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the random UUID that was generated when the GuiItem was made
     * @return The UUID
     */
    UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the action to do when the player clicks on it
     * @return The action
     */
    GuiClickResolver getAction() {
        return action;
    }

}
