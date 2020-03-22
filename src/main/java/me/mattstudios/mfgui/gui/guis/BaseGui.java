package me.mattstudios.mfgui.gui.guis;

import com.google.common.annotations.Beta;
import me.mattstudios.mfgui.gui.components.GuiAction;
import me.mattstudios.mfgui.gui.components.GuiException;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"UnusedReturnValue", "unused", "BooleanMethodIsAlwaysInverted"})
public abstract class BaseGui implements InventoryHolder {

    private final Plugin plugin;

    // Main inventory
    private Inventory inventory;

    // Inventory attributes
    private String title;
    private int rows;

    // Contains all items the GUI will have
    private final Map<Integer, GuiItem> guiItems = new HashMap<>();

    private final Map<Integer, GuiAction<InventoryClickEvent>> slotActions = new HashMap<>();

    // Action to execute when clicking on any item
    private GuiAction<InventoryClickEvent> defaultClickAction;

    // Action to execute when clicking on the top part of the GUI only
    private GuiAction<InventoryClickEvent> defaultTopClickAction;

    // Action to execute when dragging the item on the GUI
    private GuiAction<InventoryDragEvent> dragAction;

    // Action to execute when GUI closes
    private GuiAction<InventoryCloseEvent> closeGuiAction;

    // Action to execute when GUI opens
    private GuiAction<InventoryOpenEvent> openGuiAction;

    // Makes sure GUI listener is not registered more than once
    private static boolean registeredListener;

    // Whether or not the GUI is updating
    private boolean updating;

    // Whether or not the GUI should automatically update
    private boolean autoUpdate = false;

    // BukkitTask for auto updating
    private BukkitTask id;

    /**
     * Main GUI constructor
     *
     * @param plugin The plugin
     * @param rows   How many rows you want
     * @param title  The GUI's title
     */
    public BaseGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;

        this.plugin = plugin;
        this.rows = finalRows;
        this.title = title;

        inventory = Bukkit.createInventory(this, this.rows * 9, title);

        // Registers the event handler once
        if (!registeredListener) {
            Bukkit.getPluginManager().registerEvents(new GuiListener(plugin), plugin);
            registeredListener = true;
        }
    }

    /**
     * GUI constructor with only title for easier 1 row GUIs
     *
     * @param plugin The plugin
     * @param title  The GUI's title
     */
    public BaseGui(@NotNull final Plugin plugin, @NotNull final String title) {
        this(plugin, 1, title);
    }

    /**
     * Automatically updates the GUI instead of manual updates.
     *
     * @param autoUpdate    Should the auto updater be enabled
     * @param intervalTicks Update delay in ticks
     * @return The GUI
     */
    public BaseGui setAutoUpdating(boolean autoUpdate, int intervalTicks) {
        this.autoUpdate = autoUpdate;
        if (!autoUpdate || (id != null && !id.isCancelled())) return this;

        id = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!this.autoUpdate) {// If auto update is now false, exit
                id.cancel();
                return;
            }
            if (!isUpdating()) { // If not already updating
                update();
            }
        }, 0, intervalTicks);

        return this;
    }

    /**
     * Sets the number of rows the GUI should have
     *
     * @param rows The number of rows to set
     * @return The GUI
     */
    public BaseGui setRows(final int rows) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;
        this.rows = finalRows;

        updating = true;

        final List<HumanEntity> viewers = new ArrayList<>(inventory.getViewers());

        inventory = Bukkit.createInventory(this, this.rows * 9, this.title);

        for (HumanEntity player : viewers) {
            open(player);
        }

        updating = false;

        return this;
    }

    /**
     * Add an item to the GUI
     *
     * @param slot    The GUI slot
     * @param guiItem The GUI item to add
     * @return The GUI
     */
    public BaseGui setItem(final int slot, @NotNull final GuiItem guiItem) {
        if (!isValidSlot(slot)) throw new GuiException("Invalid item slot!");

        guiItems.put(slot, guiItem);

        return this;
    }

    /**
     * Sets a GUI item to many slots
     *
     * @param slots   The slots in which the item should go
     * @param guiItem The Gui Item to add
     * @return The GUI
     */
    public BaseGui setItem(@NotNull final List<Integer> slots, @NotNull final GuiItem guiItem) {
        for (final int slot : slots) {
            setItem(slot, guiItem);
        }

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
    public BaseGui setItem(final int row, final int col, @NotNull final GuiItem guiItem) {
        return setItem(getSlotFromRowCol(row, col), guiItem);
    }

    /**
     * Fills top portion of the GUI
     *
     * @param guiItem GuiItem
     * @return The GUI
     */
    public BaseGui fillTop(@NotNull final GuiItem guiItem) {
        return fillTop(Collections.singletonList(guiItem));
    }

    /**
     * Fills top portion of the GUI with alternation
     *
     * @param guiItems List of GuiItems
     * @return The GUI
     */
    public BaseGui fillTop(@NotNull final List<GuiItem> guiItems) {
        final List<GuiItem> items = repeatList(guiItems, rows * 9);
        for (int i = 0; i < 9; i++) {
            if (!this.guiItems.containsKey(i)) setItem(i, items.get(i));
        }

        return this;
    }

    /**
     * Fills bottom portion of the GUI
     *
     * @param guiItem GuiItem
     * @return The GUI
     */
    public BaseGui fillBottom(@NotNull final GuiItem guiItem) {
        return fillBottom(Collections.singletonList(guiItem));
    }

    /**
     * Fills bottom portion of the GUI with alternation
     *
     * @param guiItems GuiItem
     * @return The GUI
     */
    public BaseGui fillBottom(@NotNull final List<GuiItem> guiItems) {
        final List<GuiItem> items = repeatList(guiItems, rows * 9);
        for (int i = 9; i > 0; i--) {
            if (!this.guiItems.containsKey(i)) setItem((rows * 9) - i, items.get(i));
        }

        return this;
    }

    /**
     * Fills the outside section of the GUI with a GuiItem
     *
     * @param guiItem GuiItem
     * @return The GUI
     */
    public BaseGui fillBorder(@NotNull final GuiItem guiItem) {
        return fillBorder(Collections.singletonList(guiItem));
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItems GuiItem
     * @return The GUI
     */
    public BaseGui fillBorder(@NotNull final List<GuiItem> guiItems) {
        if (rows <= 2) return this;

        final List<GuiItem> items = repeatList(guiItems, rows * 9);

        for (int i = 0; i < rows * 9; i++) {
            if ((i <= 8) || (i >= (rows * 9) - 9)
                    || i == 9 || i == 18
                    || i == 27 || i == 36
                    || i == 17 || i == 26
                    || i == 35 || i == 44)
                setItem(i, items.get(i));

        }

        return this;
    }

    /**
     * Fills rectangle from points within the GUI
     *
     * @param rowFrom Row point 1
     * @param colFrom Col point 1
     * @param rowTo   Row point 2
     * @param colTo   Col point 2
     * @param guiItem Item to fill with
     * @return The Gui
     * @author Harolds
     */
    public BaseGui fillBetweenPoints(final int rowFrom, final int colFrom, final int rowTo, final int colTo, @NotNull final GuiItem guiItem) {
        return fillBetweenPoints(rowFrom, colFrom, rowTo, colTo, Collections.singletonList(guiItem));
    }

    /**
     * Fills rectangle from points within the GUI
     *
     * @param rowFrom  Row point 1
     * @param colFrom  Col point 1
     * @param rowTo    Row point 2
     * @param colTo    Col point 2
     * @param guiItems Item to fill with
     * @return The Gui
     * @author Harolds
     */
    public BaseGui fillBetweenPoints(final int rowFrom, final int colFrom, final int rowTo, final int colTo, @NotNull final List<GuiItem> guiItems) {
        int minRow = Math.min(rowFrom, rowTo);
        int maxRow = Math.max(rowFrom, rowTo);
        int minCol = Math.min(colFrom, colTo);
        int maxCol = Math.max(colFrom, colTo);

        final List<GuiItem> items = repeatList(guiItems, rows * 9);

        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= 9; col++) {
                int slot = getSlotFromRowCol(row, col);
                if (!((row >= minRow && row <= maxRow) && (col >= minCol && col <= maxCol)))
                    continue;

                setItem(slot, items.get(slot));
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
    public BaseGui fill(@NotNull final GuiItem guiItem) {
        return fill(Collections.singletonList(guiItem));
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItems GuiItem
     * @return The GUI
     */
    public BaseGui fill(@NotNull final List<GuiItem> guiItems) {
        final List<GuiItem> items = repeatList(guiItems, rows * 9);
        for (int i = 0; i < (rows) * 9; i++) {
            if (!this.guiItems.containsKey(i)) setItem(i, items.get(i));
        }

        return this;
    }

    /**
     * Adds items to the GUI without specific slot
     *
     * @param items The Gui Items
     * @return The GUI
     */
    public BaseGui addItem(@NotNull final GuiItem... items) {
        for (final GuiItem guiItem : items) {
            for (int slot = 0; slot < rows * 9; slot++) {
                if (guiItems.get(slot) != null) continue;

                guiItems.put(slot, guiItem);
                break;
            }
        }

        return this;
    }

    /**
     * Sets the action of a default click on any item
     *
     * @param defaultClickAction Action to resolve
     * @return The GUI
     */
    public BaseGui setDefaultClickAction(final GuiAction<InventoryClickEvent> defaultClickAction) {
        this.defaultClickAction = defaultClickAction;

        return this;
    }

    /**
     * Sets the action of a default click on any item on the top part of the GUI
     *
     * @param defaultTopClickAction Action to resolve
     * @return The GUI
     */
    public BaseGui setDefaultTopClickAction(final GuiAction<InventoryClickEvent> defaultTopClickAction) {
        this.defaultTopClickAction = defaultTopClickAction;

        return this;
    }

    /**
     * Sets the action of a default drag action
     *
     * @param dragAction Action to resolve
     * @return The GUI
     */
    public BaseGui setDragAction(final GuiAction<InventoryDragEvent> dragAction) {
        this.dragAction = dragAction;

        return this;
    }

    /**
     * Sets the action of what to do when GUI closes
     *
     * @param closeGuiAction Action to resolve
     * @return The GUI
     */
    public BaseGui setCloseGuiAction(final GuiAction<InventoryCloseEvent> closeGuiAction) {
        this.closeGuiAction = closeGuiAction;

        return this;
    }

    /**
     * Sets the action of what to do when GUI opens
     *
     * @param openGuiAction Action to resolve
     * @return The GUI
     */
    public BaseGui setOpenGuiAction(final GuiAction<InventoryOpenEvent> openGuiAction) {
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
    public BaseGui addSlotAction(final int slot, final GuiAction<InventoryClickEvent> slotAction) {
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
    public BaseGui addSlotAction(final int row, final int col, final GuiAction<InventoryClickEvent> slotAction) {
        return addSlotAction(getSlotFromRowCol(row, col), slotAction);
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
     * Sets the updating status of the GUI
     */
    public void setUpdating(final boolean updating) {
        this.updating = updating;
    }

    /**
     * Opens the GUI for a player
     *
     * @param player The player to open it to
     */
    public void open(@NotNull final HumanEntity player) {
        inventory.clear();

        for (final Map.Entry<Integer, GuiItem> entry : getGuiItems().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }

        player.openInventory(inventory);
    }

    /**
     * Closes the gui
     *
     * @param player The player to close the GUI to
     */
    public void close(@NotNull final Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, player::closeInventory, 2L);
    }

    /**
     * Method to update the current opened GUI
     */
    public void update() {
        updating = true;

        for (final HumanEntity player : inventory.getViewers()) {
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
    public void updateItem(final int slot, @NotNull final ItemStack itemStack) {
        if (!guiItems.containsKey(slot)) return;
        guiItems.get(slot).setItemStack(itemStack);
        inventory.setItem(slot, guiItems.get(slot).getItemStack());
    }

    /**
     * Used for updating the current item in the GUI at runtime
     *
     * @param row       The row of the slot
     * @param col       The col of the slot
     * @param itemStack The new ItemStack
     */
    public void updateItem(final int row, final int col, @NotNull final ItemStack itemStack) {
        updateItem(getSlotFromRowCol(row, col), itemStack);
    }

    /**
     * Updates the title of the GUI
     * This method may cause LAG if used on a loop
     *
     * @param title The title to set
     */
    @Beta
    public void updateTitle(@NotNull final String title) {
        this.title = title;

        updating = true;

        final List<HumanEntity> viewers = new ArrayList<>(inventory.getViewers());

        inventory = Bukkit.createInventory(this, inventory.getSize(), this.title);

        for (final HumanEntity player : viewers) {
            open(player);
        }

        updating = false;
    }

    /**
     * Gets map with all the GUI items
     *
     * @return The map with all the items
     */
    public Map<Integer, GuiItem> getGuiItems() {
        return guiItems;
    }

    /**
     * Gets the main inventory holder
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the amount of rows
     *
     * @return The amount of rows the GUI has
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the default click resolver
     */
    GuiAction<InventoryClickEvent> getDefaultClickAction() {
        return defaultClickAction;
    }

    /**
     * Gets the default top click resolver
     */
    GuiAction<InventoryClickEvent> getDefaultTopClickAction() {
        return defaultTopClickAction;
    }

    /**
     * Gets the default drag action
     */
    GuiAction<InventoryDragEvent> getDragAction() {
        return dragAction;
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
     *
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
     * @param guiItems  Array Type
     * @param newLength Length of array
     * @return New array
     */
    private List<GuiItem> repeatList(@NotNull final List<GuiItem> guiItems, final int newLength) {
        final List<GuiItem> repeated = new ArrayList<>();
        Collections.nCopies(rows * 9, guiItems).forEach(repeated::addAll);
        return repeated;
    }


}
