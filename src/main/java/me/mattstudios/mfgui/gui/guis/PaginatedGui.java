package me.mattstudios.mfgui.gui.guis;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public final class PaginatedGui extends BaseGui {

    private final List<GuiItem> pageItems = new ArrayList<>();
    private final Map<Integer, GuiItem> currentPage = new HashMap<>();
    private final Map<Integer, Integer> twe = new HashMap<>();

    private int pageSize;
    private int page = 1;

    public PaginatedGui(@NotNull final Plugin plugin, final int rows, final int pageSize, @NotNull final String title) {
        super(plugin, rows, title);

        this.pageSize = pageSize;

        if (rows < 2) setRows(2);
    }

    public PaginatedGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        this(plugin, rows, 0, title);
    }

    public PaginatedGui(@NotNull final Plugin plugin, @NotNull final String title) {
        this(plugin, 2, title);
    }

    /**
     * Sets the page size
     *
     * @param pageSize The new page size
     * @return The GUI
     */
    public BaseGui setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Adds an item to the next empty slot in the GUI
     *
     * @param item The GUI item
     * @return The GUI
     */
    public BaseGui addItem(@NotNull final GuiItem item) {
        pageItems.add(item);
        return this;
    }

    /**
     * Overridden method to add the items to the page instead
     *
     * @param items The Gui Items
     * @return The GUI
     */
    @Override
    public BaseGui addItem(@NotNull final GuiItem... items) {
        pageItems.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * Overridden update method to use the paginated open
     */
    @Override
    public void update() {
        setUpdating(true);

        for (final HumanEntity player : getInventory().getViewers()) {
            open(player, page);
        }

        setUpdating(false);
    }

    /**
     * Used for updating the current item in the page at runtime
     *
     * @param slot      The slot of the item to update
     * @param itemStack The new ItemStack
     */
    public void updatePageItem(final int slot, @NotNull final ItemStack itemStack) {
        if (!currentPage.containsKey(slot)) return;
        final GuiItem guiItem = currentPage.get(slot);
        guiItem.setItemStack(itemStack);
        getInventory().setItem(slot, guiItem.getItemStack());
    }

    /**
     * Used for updating the current item in the page at runtime
     *
     * @param row       The row of the slot
     * @param col       The col of the slot
     * @param itemStack The new ItemStack
     */
    public void updatePageItem(final int row, final int col, @NotNull final ItemStack itemStack) {
        updateItem(getSlotFromRowCol(row, col), itemStack);
    }

    /**
     * Used for updating the current item in the page at runtime
     *
     * @param slot The slot of the item to update
     * @param item The new ItemStack
     */
    public void updatePageItem(final int slot, @NotNull final GuiItem item) {
        if (!currentPage.containsKey(slot)) return;
        currentPage.put(slot, item);
        getInventory().setItem(slot, item.getItemStack());
    }

    /**
     * Used for updating the current item in the page at runtime
     *
     * @param row  The row of the slot
     * @param col  The col of the slot
     * @param item The new ItemStack
     */
    public void updatePageItem(final int row, final int col, @NotNull final GuiItem item) {
        updateItem(getSlotFromRowCol(row, col), item);
    }

    /**
     * Opens the GUI in the first page
     *
     * @param player The player to open it to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        open(player, 1);
    }

    /**
     * Overridden open method to add the gui page items
     *
     * @param player   The player to open it to
     * @param openPage The specific page to open at
     */
    public void open(@NotNull final HumanEntity player, final int openPage) {
        if (openPage <= getPagesNum() || openPage > 0) page = openPage;

        getInventory().clear();
        currentPage.clear();

        populateGui();

        if (pageSize == 0) pageSize = calculatePageSize();

        populatePage();

        player.openInventory(getInventory());
    }

    /**
     * Gets the items on the current page
     *
     * @return The map with the items
     */
    public Map<Integer, GuiItem> getCurrentPageItems() {
        return currentPage;
    }

    /**
     * Gets all the items added to the GUI
     *
     * @return The list with all the items
     */
    public List<GuiItem> getPageItems() {
        return pageItems;
    }


    /**
     * Gets the current page number
     *
     * @return The current page number
     */
    public int getCurrentPageNum() {
        return page;
    }

    /**
     * Gets the next page number
     *
     * @return The next page number or -1 as no next
     */
    public int getNextPageNum() {
        if (page + 1 > getPagesNum()) return page;
        return page + 1;
    }

    /**
     * Gets the previous page number
     *
     * @return The previous page number or -1 as no previous
     */
    public int getPrevPageNum() {
        if (page - 1 == 0) return page;
        return page - 1;
    }

    /**
     * Goes to the next page
     */
    public boolean nextPage() {
        if (page + 1 > getPagesNum()) return false;

        page++;
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     */
    public boolean prevPage() {
        if (page - 1 == 0) return false;

        page--;
        updatePage();
        return true;
    }

    /**
     * Gets the page item for the GUI listener
     *
     * @param slot The slot to get
     * @return The GuiItem on that slot
     */
    GuiItem getPageItem(final int slot) {
        return currentPage.get(slot);
    }

    /**
     * Gets the items in the page
     *
     * @param givenPage The page to get
     * @return A list with all the page items
     */
    private List<GuiItem> getPage(final int givenPage) {
        final int page = givenPage - 1;

        final List<GuiItem> guiPage = new ArrayList<>();

        int max = ((page * pageSize) + pageSize);
        if (max > pageItems.size()) max = pageItems.size();

        for (int i = page * pageSize; max > i; i++) {
            guiPage.add(pageItems.get(i));
        }

        return guiPage;
    }

    /**
     * Gets the number of pages the GUI has
     *
     * @return The pages number
     */
    private int getPagesNum() {
        return (int) Math.ceil((double) pageItems.size() / pageSize);
    }

    /**
     * Populates the inventory with the page items
     */
    private void populatePage() {
        // Adds the paginated items to the page
        for (final GuiItem guiItem : getPage(page)) {
            for (int slot = 0; slot < getRows() * 9; slot++) {
                if (getInventory().getItem(slot) != null) continue;
                currentPage.put(slot, guiItem);
                getInventory().setItem(slot, guiItem.getItemStack());
                break;
            }
        }
    }

    /**
     * Clears the page content
     *
     * @since 2.2.5
     */
    private void clearPage() {
        for (Map.Entry<Integer, GuiItem> entry : currentPage.entrySet()) {
            getInventory().setItem(entry.getKey(), null);
        }
    }

    /**
     * Updates the page content
     *
     * @since 2.2.5
     */
    private void updatePage() {
        clearPage();
        populatePage();
    }

    private int calculatePageSize() {
        int counter = 0;

        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getInventory().getItem(slot) == null) counter++;
        }

        return counter;
    }
}
