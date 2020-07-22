package me.mattstudios.mfgui.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GUI that does not clear items after reopening
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PaginatedGui extends BaseGui {

    private final List<GuiItem> pageItems = new ArrayList<>();
    private final Map<Integer, GuiItem> currentPage = new HashMap<>();

    private int pageSize;
    private int pageNum = 1;

    public PaginatedGui(@NotNull final Plugin plugin, final int rows, final int pageSize, @NotNull final String title) {
        super(rows, title);

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
        getInventory().clear();
        populateGui();

        updatePage();
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
        if (openPage <= getPagesNum() || openPage > 0) pageNum = openPage;

        getInventory().clear();
        currentPage.clear();

        populateGui();

        if (pageSize == 0) pageSize = calculatePageSize();

        populatePage();

        player.openInventory(getInventory());
    }

    /**
     * Updates the title of the GUI
     * This method may cause LAG if used on a loop
     *
     * @param title The title to set
     * @return The GUI
     */
    @Override
    public BaseGui updateTitle(@NotNull final String title) {
        Bukkit.broadcastMessage("Updating");
        setTitle(title);

        setUpdating(true);

        final List<HumanEntity> viewers = new ArrayList<>(getInventory().getViewers());

        setInventory(Bukkit.createInventory(this, getInventory().getSize(), title));

        for (final HumanEntity player : viewers) {
            open(player, getCurrentPageNum());
        }

        setUpdating(false);

        return this;
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
        return pageNum;
    }

    /**
     * Gets the next page number
     *
     * @return The next page number or -1 as no next
     */
    public int getNextPageNum() {
        if (pageNum + 1 > getPagesNum()) return pageNum;
        return pageNum + 1;
    }

    /**
     * Gets the previous page number
     *
     * @return The previous page number or -1 as no previous
     */
    public int getPrevPageNum() {
        if (pageNum - 1 == 0) return pageNum;
        return pageNum - 1;
    }

    /**
     * Goes to the next page
     */
    public boolean next() {
        if (pageNum + 1 > getPagesNum()) return false;

        pageNum++;
        updatePage();
        return true;
    }

    /**
     * Goes to the next page
     *
     * @deprecated Use {@link #next()} instead
     */
    @Deprecated
    public boolean nextPage() {
        return next();
    }

    /**
     * Goes to the previous page if possible
     */
    public boolean previous() {
        if (pageNum - 1 == 0) return false;

        pageNum--;
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     *
     * @deprecated Use {@link #previous()} instead
     */
    @Deprecated
    public boolean prevPage() {
        return previous();
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
    private List<GuiItem> getPageNum(final int givenPage) {
        final int page = givenPage - 1;

        final List<GuiItem> guiPage = new ArrayList<>();

        int max = ((page * pageSize) + pageSize);
        if (max > pageItems.size()) max = pageItems.size();

        for (int i = page * pageSize; i < max; i++) {
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
        for (final GuiItem guiItem : getPageNum(pageNum)) {
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
    void clearPage() {
        for (Map.Entry<Integer, GuiItem> entry : currentPage.entrySet()) {
            getInventory().setItem(entry.getKey(), null);
        }
    }

    /**
     * Gets the page size
     *
     * @return The page size
     */
    int getPageSize() {
        return pageSize;
    }

    /**
     * Gets the page number
     *
     * @return The current page number
     */
    int getPageNum() {
        return pageNum;
    }

    /**
     * Sets the page number
     *
     * @param pageNum Sets the current page to be the specified number
     */
    void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Updates the page content
     *
     * @since 2.2.5
     */
    void updatePage() {
        clearPage();
        populatePage();
    }

    /**
     * Calculates the size of the give page
     *
     * @return The page size
     */
    int calculatePageSize() {
        int counter = 0;

        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getInventory().getItem(slot) == null) counter++;
        }

        return counter;
    }

}
