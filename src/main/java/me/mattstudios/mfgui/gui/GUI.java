package me.mattstudios.mfgui.gui;

import com.google.common.annotations.Beta;
import me.mattstudios.mfgui.gui.components.GuiAction;
import me.mattstudios.mfgui.gui.components.GuiException;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class GUI implements InventoryHolder {

    // Main inventory
    private Inventory inventory;

    // Inventory attributes
    private String title;
    private int rows;

    // Contains all items the GUI will have
    private final Map<Integer, GuiItem> guiItems;

    private final Map<Integer, GuiAction<InventoryClickEvent>> slotActions;

    // Action to execute when clicking on any item
    private GuiAction<InventoryClickEvent> defaultClickAction;

    // Action to execute when GUI closes
    private GuiAction<InventoryCloseEvent> closeGuiAction;

    // Action to execute when GUI opens
    private GuiAction<InventoryOpenEvent> openGuiAction;

    // Makes sure GUI listener is not registered more than once
    private static boolean registeredListener;

    // Whether or not the GUI is updating
    private boolean updating;

    /**
     * Main GUI constructor
     *
     * @param plugin The plugin
     * @param rows   How many rows you want
     * @param title  The GUI's title
     */
    public GUI(final Plugin plugin, int rows, final String title) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;

        this.rows = finalRows;
        this.title = title;

        guiItems = new HashMap<>();
        slotActions = new HashMap<>();

        inventory = Bukkit.createInventory(this, rows * 9, title);

        // Registers the event handler once
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
     * Sets the number of rows the GUI should have
     *
     * @param rows The number of rows to set
     * @return The GUI
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
     * @return The GUI
     */
    public GUI setItem(final int slot, final GuiItem guiItem) {
        if (!isValidSlot(slot)) throw new GuiException("Invalid item slot!");

        guiItems.put(slot, guiItem);

        return this;
    }

    /**
     * Add an item to the GUI
     *
     * @param row     The GUI row number
     * @param col     The GUI col number
     * @param guiItem The GUI item to add
     * @return The GUI
     */
    public GUI setItem(final int row, final int col, final GuiItem guiItem) {
        return setItem(getSlotFromRowCol(row, col), guiItem);
    }

    /**
     * Fills top portion of the GUI
     *
     * @param guiItem GuiItem
     * @return        The GUI
     */
    public GUI fillTop(final GuiItem guiItem) {
        fillTop(Collections.singletonList(guiItem));
        return this;
    }

    /**
     * Fills top portion of the GUI with alternation
     *
     * @param guiItems List of GuiItems
     * @return         The GUI
     */
    public GUI fillTop(final List<GuiItem> guiItems) {
        GuiItem[] items = guiItems.toArray(new GuiItem[0]);
        items = repeatArray(items, rows * 9);
        for (int i = 0; i >= 0 && i <= 8; i++) {
            if (!this.guiItems.containsKey(i))
                setItem(i, items[i]);
        }

        return this;
    }

    /**
     * Fills bottom portion of the GUI
     *
     * @param guiItem GuiItem
     * @return        The GUI
     */
    public GUI fillBottom(final GuiItem guiItem) {
        fillTop(Collections.singletonList(guiItem));
        return this;
    }

    /**
     * Fills bottom portion of the GUI with alternation
     *
     * @param guiItems GuiItem
     * @return         The GUI
     */
    public GUI fillBottom(final List<GuiItem> guiItems) {
        GuiItem[] items = guiItems.toArray(new GuiItem[0]);
        items = repeatArray(items, rows * 9);
        for (int i = 0; i < 53; i++) {
            if (i >= (rows * 9) - 9 && i <= (rows * 9)) {
                if (this.guiItems.containsKey(i) || !isValidSlot(i)) continue;
                    setItem(i, items[i]);
            }
        }

        return this;
    }

    /**
     * Sets an GuiItem to fill up the entire inventory where there is no other item
     *
     * @param guiItem The item to use as fill
     * @return The GUI
     */
    public GUI setFillItem(final GuiItem guiItem) {
        setFillItems(Collections.singletonList(guiItem));
        return this;
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItemList GuiItem
     */
    public GUI setFillItems(final List<GuiItem> guiItemList) {
        GuiItem[] items = guiItemList.toArray(new GuiItem[0]);
        items = repeatArray(items, rows * 9);
        int i = -1;
        for (GuiItem item : items) {
            i++;
            if (i > rows * 9) break;
            if (this.guiItems.containsKey(i) || !isValidSlot(i)) continue;
            setItem(i, items[i]);
        }
        return this;
    }

    /**
     * Sets the action of a default click on any item
     *
     * @param defaultClickAction Action to resolve
     * @return The GUI
     */
    public GUI setDefaultClickAction(final GuiAction<InventoryClickEvent> defaultClickAction) {
        this.defaultClickAction = defaultClickAction;

        return this;
    }

    /**
     * Sets the action of what to do when GUI closes
     *
     * @param closeGuiAction Action to resolve
     * @return The GUI
     */
    public GUI setCloseGuiAction(final GuiAction<InventoryCloseEvent> closeGuiAction) {
        this.closeGuiAction = closeGuiAction;

        return this;
    }

    /**
     * Sets the action of what to do when GUI opens
     *
     * @param openGuiAction Action to resolve
     * @return The GUI
     */
    public GUI setOpenGuiAction(final GuiAction<InventoryOpenEvent> openGuiAction) {
        this.openGuiAction = openGuiAction;

        return this;
    }

    /**
     * Adds a Gui Action for when clicking on a specific slot
     *
     * @param slot       The slot to add
     * @param slotAction The gui action
     * @return The GUI
     */
    public GUI addSlotAction(final int slot, final GuiAction<InventoryClickEvent> slotAction) {
        if (!isValidSlot(slot)) return this;
        slotActions.put(slot, slotAction);

        return this;
    }

    /**
     * Adds a Gui Action for when clicking on a specific slot with row and col instead
     *
     * @param row        The row of the slot
     * @param col        The col of the slot
     * @param slotAction The gui action
     * @return The GUI
     */
    public GUI addSlotAction(final int row, final int col, final GuiAction<InventoryClickEvent> slotAction) {
        return addSlotAction(getSlotFromRowCol(row, col), slotAction);
    }

    /**
     * Fills the outside section of the GUI with a GuiItem
     *
     * @param guiItem GuiItem
     */
    public GUI fillBorder(final GuiItem guiItem) {
        fillBorder(Collections.singletonList(guiItem));
        return this;
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItemList GuiItem
     */
    public GUI fillBorder(final List<GuiItem> guiItemList) {
        GuiItem[] items = guiItemList.toArray(new GuiItem[0]);
        items = repeatArray(items, rows * 9);
        if (rows == 1 || rows == 2) return this;
        for (int i = 0; i <= rows * 9; i++) {
            if (((i >= 0 && i <= 8) || (i >= (rows * 9) - 9 && i <= (rows * 9))
                    || i == 9 || i == 18
                    || i == 27|| i == 36
                    || i == 17 || i == 26
                    || i == 35 || i == 45) && isValidSlot(i))
                        setItem(i, items[i]);

        }

        return this;
    }

    /**
     * Gets a specific GuiItem on the slot
     *
     * @param slot The slot to get
     * @return The GUI
     */
    public GuiItem getGuiItem(final int slot) {
        return isValidSlot(slot) ? guiItems.get(slot) : null;
    }

    /**
     * Checks weather or not the GUI is updating
     *
     * @return If it's updating or not
     */
    public boolean isUpdating() {
        return updating;
    }

    /**
     * Opens the GUI for a player
     *
     * @param player The player to open it to
     */
    public void open(final HumanEntity player) {
        inventory.clear();

        for (final int slot : guiItems.keySet()) {
            inventory.setItem(slot, guiItems.get(slot).getItemStack());
        }

        player.openInventory(inventory);
    }

    /**
     * Method to update the current opened GUI
     */
    public void update() {
        updating = true;

        for (HumanEntity player : inventory.getViewers()) {
            open(player);
        }

        updating = false;
    }

    /**
     * Used for updating the current item in the GUI at runtime
     *
     * @param slot      The slot of the item to update
     * @param itemStack The new ItemStack
     */
    public void updateItem(final int slot, final ItemStack itemStack) {
        if (!guiItems.containsKey(slot)) return;
        guiItems.get(slot).setItemStack(itemStack);
        inventory.setItem(slot, guiItems.get(slot).getItemStack());
    }

    /**
     * Used for updating the current item in the GUI at runtime
     *
     * @param row        The row of the slot
     * @param col        The col of the slot
     * @param itemStack The new ItemStack
     */
    public void updateItem(final int row, final int col, final ItemStack itemStack) {
        updateItem(getSlotFromRowCol(row, col), itemStack);
    }

    /**
     * Updates the title of the GUI
     * This method may cause LAG if used on a loop
     *
     * @param title The title to set
     */
    @Beta
    public void updateTitle(final String title) {
        this.title = title;

        updating = true;

        final List<HumanEntity> viewers = new ArrayList<>(inventory.getViewers());

        inventory = Bukkit.createInventory(this, inventory.getSize(), this.title);

        for (HumanEntity player : viewers) {
            open(player);
        }

        updating = false;
    }

    /**
     * Gets the main inventory holder
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the default click resolver
     */
    GuiAction<InventoryClickEvent> getDefaultClickAction() {
        return defaultClickAction;
    }

    /**
     * Gets the close gui resolver
     */
    GuiAction<InventoryCloseEvent> getCloseGuiAction() {
        return closeGuiAction;
    }

    /**
     * Gets the open gui resolver
     */
    GuiAction<InventoryOpenEvent> getOpenGuiAction() {
        return openGuiAction;
    }

    /**
     * Gets the action for the specified slot
     *
     * @param slot The slot clicked
     */
    GuiAction<InventoryClickEvent> getSlotAction(final int slot) {
        return isValidSlot(slot) ? slotActions.get(slot) : null;
    }

    /**
     * Checks if the slot introduces is a valid slot
     *
     * @param slot The slot to check
     */
    private boolean isValidSlot(final int slot) {
        return slot >= 0 && slot < rows * 9;
    }

    /**
     * Gets the slot from the row and col passed
     * @param row The row
     * @param col The col
     * @return The new slot
     */
    private int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }

    /**
     * Repeats any type of Array. Allows for alternating items
     * Stores references to existing Objects -> Does not create new objects
     *
     * @param arr       Array Type
     * @param newLength Length of array
     * @return          New array
     */
    private static <T> T[] repeatArray(T[] arr, int newLength) {
        T[] dup = Arrays.copyOf(arr, newLength);
        for (int last = arr.length; last != 0 && last < newLength; last <<= 1) {
            System.arraycopy(dup, 0, dup, last, Math.min(last << 1, newLength) - last);
        }
        return dup;
    }

}
