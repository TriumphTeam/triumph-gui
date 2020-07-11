package me.mattstudios.mfgui.gui.guis;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * GUI that allows you to have multiple pages of items
 */
@SuppressWarnings("unused")
public final class PersistentGui extends BaseGui {

    public PersistentGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(plugin, rows, title);
    }

    public PersistentGui(@NotNull final Plugin plugin, @NotNull final String title) {
        super(plugin, title);
    }

    /**
     * Adds ItemStacks to the inventory straight, not GUI
     *
     * @param items The items
     * @return The left overs
     */
    public Map<Integer, ItemStack> addItem(@NotNull final ItemStack... items) {
        return getInventory().addItem(items);
    }

    /**
     * Opens the GUI for a player
     *
     * @param player The player to open it to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        populateGui();
        player.openInventory(getInventory());
    }

}
