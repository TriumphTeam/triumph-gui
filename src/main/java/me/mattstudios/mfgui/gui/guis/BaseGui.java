package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiAction;
import me.mattstudios.mfgui.gui.components.GuiException;
import me.mattstudios.mfgui.gui.components.GuiFiller;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"UnusedReturnValue", "unused", "BooleanMethodIsAlwaysInverted"})
public abstract class BaseGui implements InventoryHolder {

    // Makes sure GUI listener is not registered more than once
    private static boolean registeredListener;
    private final Plugin plugin;
    // Contains all items the GUI will have
    private final Map<Integer, GuiItem> guiItems = new HashMap<>();
    private final Map<Integer, GuiAction<InventoryClickEvent>> slotActions = new HashMap<>();
    // Main inventory
    private Inventory inventory;
    // Gui filler
    private GuiFiller filler = new GuiFiller(this);
    // Inventory attributes
    private InventoryType type;
    private String title;
    private int rows;
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
    // Action to execute when clicked outside the GUI
    private GuiAction<InventoryClickEvent> outsideClickAction;
    // Whether or not the GUI is updating
    private boolean updating;

    /**
     * Main GUI constructor
     *
     * @param plugin The plugin
     * @param rows   How many rows you want
     * @param title  The GUI's title
     */
    public BaseGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) {
            finalRows = 1;
        }

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
     * Sub GUI constructor
     *
     * @param plugin The plugin
     * @param type   GUI's Inventory type
     * @param title  The GUI's title
     */
    public BaseGui(@NotNull final Plugin plugin, final InventoryType type, @NotNull final String title) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;

        inventory = Bukkit.createInventory(this, this.type, title);

        // Register the event handler once
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
     * Add an item to the GUI
     *
     * @param slot    The GUI slot
     * @param guiItem The GUI item to add
     * @return The GUI
     */
    public BaseGui setItem(final int slot, @NotNull final GuiItem guiItem) {
        if (type == null) {
            if (!isValidSlot(slot)) {
                throw new GuiException("Invalid item slot! (" + slot + ")");
            }
        } else {
            if (!isValidSlot(slot, type)) {
                throw new GuiException("Invalid item slot! (" + slot + ")");
            }
        }

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
     * Adds items to the GUI without specific slot
     *
     * @param items The Gui Items
     * @return The GUI
     */
    public BaseGui addItem(@NotNull final GuiItem... items) {
        for (final GuiItem guiItem : items) {
            for (int slot = 0; slot < rows * 9; slot++) {
                if (guiItems.get(slot) != null) {
                    continue;
                }

                guiItems.put(slot, guiItem);
                break;
            }
        }

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
        if (!isValidSlot(slot)) {
            return this;
        }
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
        if (type == null) {
            return isValidSlot(slot) ? guiItems.get(slot) : null;
        } else {
            return isValidSlot(slot, type) ? guiItems.get(slot) : null;
        }
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
     *
     * @param updating Sets updating
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
        populateGui();
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
        if (!guiItems.containsKey(slot)) {
            return;
        }
        final GuiItem guiItem = guiItems.get(slot);
        guiItem.setItemStack(itemStack);
        inventory.setItem(slot, guiItem.getItemStack());
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
     * Used for updating the current item in the GUI at runtime
     *
     * @param slot The slot of the item to update
     * @param item The new ItemStack
     */
    public void updateItem(final int slot, @NotNull final GuiItem item) {
        if (!guiItems.containsKey(slot)) {
            return;
        }
        guiItems.put(slot, item);
        inventory.setItem(slot, item.getItemStack());
    }

    /**
     * Used for updating the current item in the GUI at runtime
     *
     * @param row  The row of the slot
     * @param col  The col of the slot
     * @param item The new ItemStack
     */
    public void updateItem(final int row, final int col, @NotNull final GuiItem item) {
        updateItem(getSlotFromRowCol(row, col), item);
    }

    /**
     * Updates the title of the GUI
     * This method may cause LAG if used on a loop
     *
     * @param title The title to set
     * @return The GUI
     */
    public BaseGui updateTitle(@NotNull final String title) {
        this.title = title;

        updating = true;

        final List<HumanEntity> viewers = new ArrayList<>(inventory.getViewers());

        inventory = Bukkit.createInventory(this, inventory.getSize(), this.title);

        for (final HumanEntity player : viewers) {
            open(player);
        }

        updating = false;

        return this;
    }

    /**
     * Return the GUI filler with the filling methods
     *
     * @return The GUI filler
     */
    public GuiFiller getFiller() {
        return filler;
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
     * Sets the number of rows the GUI should have
     *
     * @param rows The number of rows to set
     * @return The GUI
     */
    public BaseGui setRows(final int rows) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) {
            finalRows = 1;
        }
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
     * Gets the default click resolver
     */
    GuiAction<InventoryClickEvent> getDefaultClickAction() {
        return defaultClickAction;
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
     * Gets the default top click resolver
     */
    GuiAction<InventoryClickEvent> getDefaultTopClickAction() {
        return defaultTopClickAction;
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
     * Gets the default drag action
     */
    GuiAction<InventoryDragEvent> getDragAction() {
        return dragAction;
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
     * Gets the close gui resolver
     */
    GuiAction<InventoryCloseEvent> getCloseGuiAction() {
        return closeGuiAction;
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
     * Gets the open gui resolver
     */
    GuiAction<InventoryOpenEvent> getOpenGuiAction() {
        return openGuiAction;
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
     * Gets the gui action for the outside click
     */
    GuiAction<InventoryClickEvent> getOutsideClickAction() {
        return outsideClickAction;
    }

    /**
     * Sets the action of when clicking on the outside of the inventory
     *
     * @param outsideClickAction Action to resolve
     * @return The GUI
     */
    public BaseGui setOutsideClickAction(final GuiAction<InventoryClickEvent> outsideClickAction) {
        this.outsideClickAction = outsideClickAction;
        return this;
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
     * Populates the GUI with it's items
     */
    void populateGui() {
        for (final Map.Entry<Integer, GuiItem> entry : getGuiItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }

    /**
     * Checks if the slot introduces is a valid slot
     *
     * @param slot The slot to check
     */
    private boolean isValidSlot(final int slot) {
        return slot >= 0 && slot < rows * 9;
    }

    private boolean isValidSlot(final int slot, final InventoryType type) {
        return slot >= 0 && slot < type.getDefaultSize();
    }

    /**
     * Gets the slot from the row and col passed
     *
     * @param row The row
     * @param col The col
     * @return The new slot
     */
    int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }


}
