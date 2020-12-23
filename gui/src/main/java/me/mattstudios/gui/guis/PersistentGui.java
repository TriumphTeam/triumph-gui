package me.mattstudios.gui.guis;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * GUI that does not clear it's items once it's closed
 */
@SuppressWarnings("unused")
public class PersistentGui extends BaseGui {

    /**
     * Main constructor of the Persistent GUI
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     */
    public PersistentGui(final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that does not require rows
     *
     * @param title The GUI's title
     */
    public PersistentGui(@NotNull final String title) {
        super(1, title);
    }

    /**
     * Old constructor
     *
     * @param plugin The plugin's instance
     * @param rows   The rows the GUI should have
     * @param title  The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #PersistentGui(int, String)} instead
     */
    @Deprecated
    public PersistentGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Old constructor
     *
     * @param plugin The plugin's instance
     * @param title  The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #PersistentGui(String)} instead
     */
    @Deprecated
    public PersistentGui(@NotNull final Plugin plugin, @NotNull final String title) {
        super(1, title);
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    public Map<Integer, ItemStack> addItem(@NotNull final ItemStack... items) {
        return Collections.unmodifiableMap(getInventory().addItem(items));
    }

    /**
     * Overridden {@link BaseGui#open(HumanEntity)} to prevent
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        if (player.isSleeping()) return;
        populateGui();
        player.openInventory(getInventory());
    }

}
