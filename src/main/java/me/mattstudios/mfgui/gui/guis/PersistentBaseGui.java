package me.mattstudios.mfgui.gui.guis;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;

@SuppressWarnings("unused")
public final class PersistentBaseGui extends BaseGui {

    public PersistentBaseGui(final Plugin plugin, final int rows, final String title) {
        super(plugin, rows, title);
    }

    /**
     * Adds ItemStacks to the inventory straight, not GUI
     *
     * @param items The items
     * @return The left overs
     */
    public Map<Integer, ItemStack> addItem(final ItemStack... items) {
        Validate.noNullElements(items, "Item cannot be null");
        return getInventory().addItem(items);
    }

    /**
     * Opens the GUI for a player
     *
     * @param player The player to open it to
     */
    @Override
    public void open(final HumanEntity player) {
        final Map<Integer, GuiItem> guiItems = getGuiItems();
        for (final int slot : guiItems.keySet()) {
            getInventory().setItem(slot, guiItems.get(slot).getItemStack());
        }

        player.openInventory(getInventory());
    }

}
