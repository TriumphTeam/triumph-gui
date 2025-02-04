/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.components.GuiContainer;
import dev.triumphteam.gui.components.InteractionModifier;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * GUI that allows you to have multiple pages
 */
@SuppressWarnings("unused")
public class PaginatedGui extends BaseGui {

    // List with all the page items
    private final List<GuiItem> pageItems = new ArrayList<>();
    // Saves the current page items and it's slot
    private final Map<Integer, GuiItem> currentPage;

    private int pageSize;
    private int pageNum = 1;


    public PaginatedGui(final @NotNull GuiContainer guiContainer, final int pageSize, final @NotNull Set<InteractionModifier> interactionModifiers) {
        super(guiContainer, interactionModifiers);
        this.pageSize = pageSize;
        this.currentPage = new LinkedHashMap<>(guiContainer.inventorySize());
    }

    /**
     * Sets the page size
     *
     * @param pageSize The new page size
     * @return The GUI for easier use when declaring, works like a builder
     */
    public BaseGui setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Adds an {@link GuiItem} to the next available slot in the page area
     *
     * @param item The {@link GuiItem} to add to the page
     */
    public void addItem(@NotNull final GuiItem item) {
        pageItems.add(item);
    }

    /**
     * Overridden {@link BaseGui#addItem(GuiItem...)} to add the items to the page instead
     *
     * @param items Varargs for specifying the {@link GuiItem}s
     */
    @Override
    public void addItem(@NotNull final GuiItem... items) {
        pageItems.addAll(Arrays.asList(items));
    }

    /**
     * Overridden {@link BaseGui#update()} to use the paginated open
     */
    @Override
    public void update() {
        getInventory().clear();
        populateGui();

        updatePage();
    }

    /**
     * Updates the page {@link GuiItem} on the slot in the page
     * Can get the slot from {@link InventoryClickEvent#getSlot()}
     *
     * @param slot      The slot of the item to update
     * @param itemStack The new {@link ItemStack}
     */
    public void updatePageItem(final int slot, @NotNull final ItemStack itemStack) {
        if (!currentPage.containsKey(slot)) return;
        final GuiItem guiItem = currentPage.get(slot);
        guiItem.setItemStack(itemStack);
        getInventory().setItem(slot, guiItem.getItemStack());
    }

    /**
     * Alternative {@link #updatePageItem(int, ItemStack)} that uses <i>ROWS</i> and <i>COLUMNS</i> instead
     *
     * @param row       The row of the slot
     * @param col       The columns of the slot
     * @param itemStack The new {@link ItemStack}
     */
    public void updatePageItem(final int row, final int col, @NotNull final ItemStack itemStack) {
        updateItem(getSlotFromRowCol(row, col), itemStack);
    }

    /**
     * Alternative {@link #updatePageItem(int, ItemStack)} that uses {@link GuiItem} instead
     *
     * @param slot The slot of the item to update
     * @param item The new ItemStack
     */
    public void updatePageItem(final int slot, @NotNull final GuiItem item) {
        if (!currentPage.containsKey(slot)) return;
        // Gets the old item and its index on the main items list
        final GuiItem oldItem = currentPage.get(slot);
        final int index = pageItems.indexOf(currentPage.get(slot));

        // Updates both lists and inventory
        currentPage.put(slot, item);
        pageItems.set(index, item);
        getInventory().setItem(slot, item.getItemStack());
    }

    /**
     * Alternative {@link #updatePageItem(int, GuiItem)} that uses <i>ROWS</i> and <i>COLUMNS</i> instead
     *
     * @param row  The row of the slot
     * @param col  The columns of the slot
     * @param item The new {@link GuiItem}
     */
    public void updatePageItem(final int row, final int col, @NotNull final GuiItem item) {
        updateItem(getSlotFromRowCol(row, col), item);
    }

    /**
     * Removes a given {@link GuiItem} from the page.
     *
     * @param item The {@link GuiItem} to remove.
     */
    public void removePageItem(@NotNull final GuiItem item) {
        pageItems.remove(item);
        updatePage();
    }

    /**
     * Removes a given {@link ItemStack} from the page.
     *
     * @param item The {@link ItemStack} to remove.
     */
    public void removePageItem(@NotNull final ItemStack item) {
        final Optional<GuiItem> guiItem = pageItems.stream().filter(it -> it.getItemStack().equals(item)).findFirst();
        guiItem.ifPresent(this::removePageItem);
    }

    /**
     * Overrides {@link BaseGui#open(HumanEntity)} to use the paginated populator instead
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        open(player, 1);
    }

    /**
     * Specific open method for the Paginated GUI
     * Uses {@link #populatePage()}
     *
     * @param player   The {@link HumanEntity} to open it to
     * @param openPage The specific page to open at
     */
    public void open(@NotNull final HumanEntity player, final int openPage) {
        if (player.isSleeping()) return;
        if (openPage <= getPagesNum() || openPage > 0) pageNum = openPage;

        getInventory().clear();
        currentPage.clear();

        populateGui();

        if (pageSize == 0) pageSize = calculatePageSize();

        populatePage();

        player.openInventory(getInventory());
    }

    /**
     * Overrides {@link BaseGui#updateTitle(Component)} to use the paginated populator instead
     * Updates the title of the GUI
     * <i>This method may cause LAG if used on a loop</i>
     *
     * @param title The title to set
     * @return The GUI for easier use when declaring, works like a builder
     */
    @Override
    public @NotNull BaseGui updateTitle(@NotNull final Component title) {
        setUpdating(true);

        final List<HumanEntity> viewers = new ArrayList<>(getInventory().getViewers());
        final GuiContainer guiContainer = guiContainer();

        guiContainer.title(title);
        setInventory(guiContainer.createInventory(this));

        for (final HumanEntity player : viewers) {
            open(player, getPageNum());
        }

        setUpdating(false);
        return this;
    }

    /**
     * Gets an immutable {@link Map} with all the current pages items
     *
     * @return The {@link Map} with all the {@link #currentPage}
     */
    @NotNull
    public Map<@NotNull Integer, @NotNull GuiItem> getCurrentPageItems() {
        return Collections.unmodifiableMap(currentPage);
    }

    /**
     * Gets an immutable {@link List} with all the page items added to the GUI
     *
     * @return The  {@link List} with all the {@link #pageItems}
     */
    @NotNull
    public List<@NotNull GuiItem> getPageItems() {
        return Collections.unmodifiableList(pageItems);
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
     * @return The next page number or {@link #pageNum} if no next is present
     */
    public int getNextPageNum() {
        if (pageNum + 1 > getPagesNum()) return pageNum;
        return pageNum + 1;
    }

    /**
     * Gets the previous page number
     *
     * @return The previous page number or {@link #pageNum} if no previous is present
     */
    public int getPrevPageNum() {
        if (pageNum - 1 == 0) return pageNum;
        return pageNum - 1;
    }

    /**
     * Goes to the next page
     *
     * @return False if there is no next page.
     */
    public boolean next() {
        if (pageNum + 1 > getPagesNum()) return false;

        pageNum++;
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     *
     * @return False if there is no previous page.
     */
    public boolean previous() {
        if (pageNum - 1 == 0) return false;

        pageNum--;
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
    public int getPagesNum() {
        if (pageSize == 0) pageSize = calculatePageSize();
        return (int) Math.ceil((double) pageItems.size() / pageSize);
    }

    /**
     * Populates the inventory with the page items
     */
    private void populatePage() {
        // Adds the paginated items to the page
        int slot = 0;
        final int inventorySize = getInventory().getSize();
        final Iterator<GuiItem> iterator = getPageNum(pageNum).iterator();
        while (iterator.hasNext()) {
            if (slot >= inventorySize) {
                break; // Exit the loop if slot exceeds inventory size
            }

            if (getGuiItem(slot) != null || getInventory().getItem(slot) != null) {
                slot++;
                continue;
            }

            final GuiItem guiItem = iterator.next();

            currentPage.put(slot, guiItem);
            getInventory().setItem(slot, guiItem.getItemStack());
            slot++;
        }
    }

    /**
     * Gets the current page items to be used on other gui types
     *
     * @return The {@link Map} with all the {@link #currentPage}
     */
    Map<Integer, GuiItem> getMutableCurrentPageItems() {
        return currentPage;
    }

    /**
     * Clears the page content
     */
    void clearPage() {
        for (Map.Entry<Integer, GuiItem> entry : currentPage.entrySet()) {
            getInventory().setItem(entry.getKey(), null);
        }
    }

    /**
     * Clears all previously added page items
     */
    public void clearPageItems(final boolean update) {
        pageItems.clear();
        if (update) update();
    }

    public void clearPageItems() {
        clearPageItems(false);
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
    public void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Updates the page content
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
            if (getGuiItem(slot) == null) counter++;
        }

        if (counter == 0) return 1;
        return counter;
    }
}
