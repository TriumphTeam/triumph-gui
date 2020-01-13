package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import me.mattstudios.mfgui.gui.components.GuiException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public final class GUI implements InventoryHolder {

    private Inventory inventory;

    private String title;
    private int rows;
    // Contains all items the GUI will have
    private Map<Integer, GuiItem> guiItems;

    // Resolver for default action to clicking on any item
    private GuiClickResolver defaultClick;

    // Makes sure GUI listener is not registered more than once
    private static boolean registeredListener;

    /**
     * Main GUI constructor
     *
     * @param plugin The plugin
     * @param title  The GUI's title
     */
    public GUI(final Plugin plugin, int rows, final String title) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;

        this.rows = finalRows;
        this.title = title;
        this.guiItems = new HashMap<>();

        this.inventory = Bukkit.createInventory(this, rows * 9, title);

        if (!registeredListener) {
            Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
            registeredListener = true;
        }
    }

    /**
     * GUI constructor with only title for easier 1 row GUIs
     *
     * @param plugin The plugin
     * @param title  The GUI's title
     */
    public GUI(final Plugin plugin, final String title) {
        this(plugin, 1, title);
    }


    /**
     * Sets the title of the GUI
     *
     * @param title The title to set
     */
    public GUI setTitle(final String title) {
        this.title = title;

        return this;
    }

    /**
     * Sets the number of rows the GUI should have
     *
     * @param rows The number of rows to set
     */
    public GUI setRows(final int rows) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;
        this.rows = finalRows;

        return this;
    }

    /**
     * Add an item to the GUI
     *
     * @param slot    The GUI slot
     * @param guiItem The GUI item to add
     */
    public GUI addItem(final int slot, final GuiItem guiItem) {
        if (!isValidSlot(slot)) throw new GuiException("shit");

        guiItems.put(slot, guiItem);

        return this;
    }

    /**
     * Add an item to the GUI
     *
     * @param row     The GUI row number
     * @param col     The GUI col number
     * @param guiItem The GUI item to add
     */
    public GUI addItem(final int row, final int col, final GuiItem guiItem) {
        return addItem((col + (row - 1) * 9) - 1, guiItem);
    }

    public GUI setFillItem(final GuiItem guiItem) {
        for (int i = 0; i < rows * 9; i++){
            if (guiItems.containsKey(i)) continue;
            guiItems.put(i, guiItem);
        }

        return this;
    }

    /**
     * Set's the action of a default click on any item
     *
     * @param defaultClick Action to resolve
     */
    public void setDefaultClick(final GuiClickResolver defaultClick) {
        this.defaultClick = defaultClick;
    }

    /**
     * Opens the GUI for a player
     *
     * @param player The player to open it to
     */
    public void open(final Player player) {
        inventory.clear();

        for (final int slot : guiItems.keySet()) {
            inventory.setItem(slot, guiItems.get(slot));
        }

        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the default click resolver
     *
     * @return The click resolver
     */
    GuiClickResolver getDefaultClick() {
        return defaultClick;
    }

    /**
     * Checks if the slot introduces is a valid slot
     *
     * @param slot The slot to check
     * @return If it is valid or not
     */
    private boolean isValidSlot(final int slot) {
        return slot >= 0;
    }
}
