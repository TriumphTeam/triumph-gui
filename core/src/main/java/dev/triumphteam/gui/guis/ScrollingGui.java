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

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.components.InventoryProvider;
import dev.triumphteam.gui.components.ScrollType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * GUI that allows you to scroll through items
 */
@SuppressWarnings("unused")
public class ScrollingGui extends PaginatedGui {

    private final ScrollType scrollType;
    private int scrollSize = 0;

    /**
     * Main constructor of the Scrolling GUI
     *
     * @param rows       The rows the GUI should have
     * @param pageSize   The Page size
     * @param title      The title using {@link String}
     * @param scrollType The {@link ScrollType}
     * @param interactionModifiers A set containing the {@link InteractionModifier} this GUI should use
     * @since 3.0.3
     * @author SecretX
     */
    public ScrollingGui(final int rows, final int pageSize, @NotNull final String title, @NotNull final ScrollType scrollType, @NotNull final Set<InteractionModifier> interactionModifiers) {
        super(rows, pageSize, title, interactionModifiers);

        this.scrollType = scrollType;
    }

    public ScrollingGui(final int rows, final int pageSize, @NotNull final Component title, @NotNull final InventoryProvider inventoryProvider, @NotNull final ScrollType scrollType, @NotNull final Set<InteractionModifier> interactionModifiers) {
        super(rows, pageSize, title, inventoryProvider, interactionModifiers);

        this.scrollType = scrollType;
    }

    /**
     * Main constructor of the Scrolling GUI
     *
     * @param rows       The rows the GUI should have
     * @param pageSize   The Page size
     * @param title      The GUI's title
     * @param scrollType The {@link ScrollType}
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(final int rows, final int pageSize, @NotNull final String title, @NotNull final ScrollType scrollType) {
        super(rows, pageSize, title);
        this.scrollType = scrollType;
    }

    /**
     * Alternative constructor that doesn't require the {@link ScrollType}
     *
     * @param rows     The rows the GUI should have
     * @param pageSize The Page size
     * @param title    The GUI's title
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(final int rows, final int pageSize, @NotNull final String title) {
        this(rows, pageSize, title, ScrollType.VERTICAL);
    }

    /**
     * Alternative constructor that doesn't require the {@link ScrollType} or page size
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(final int rows, @NotNull final String title) {
        this(rows, 0, title, ScrollType.VERTICAL);
    }

    /**
     * Alternative constructor that doesn't require the page size
     *
     * @param rows       The rows the GUI should have
     * @param title      The GUI's title
     * @param scrollType The {@link ScrollType}
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(final int rows, @NotNull final String title, @NotNull final ScrollType scrollType) {
        this(rows, 0, title, scrollType);
    }

    /**
     * Alternative constructor that only requires title
     *
     * @param title The GUI's title
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(@NotNull final String title) {
        this(2, title);
    }

    /**
     * Alternative constructor that doesn't require the rows or page size
     *
     * @param title      The GUI's title
     * @param scrollType The {@link ScrollType}
     * @deprecated In favor of {@link ScrollingGui#ScrollingGui(int, int, String, ScrollType, Set)}
     */
    @Deprecated
    public ScrollingGui(@NotNull final String title, @NotNull final ScrollType scrollType) {
        this(2, title, scrollType);
    }

    /**
     * Overrides {@link PaginatedGui#next()} to make it work with the specific scrolls
     */
    @Override
    public boolean next() {
        if (getPageNum() * scrollSize + getPageSize() >= getPageItems().size() + scrollSize) return false;

        setPageNum(getPageNum() + 1);
        updatePage();
        return true;
    }

    /**
     * Overrides {@link PaginatedGui#previous()} to make it work with the specific scrolls
     */
    @Override
    public boolean previous() {
        if (getPageNum() - 1 == 0) return false;

        setPageNum(getPageNum() - 1);
        updatePage();
        return true;
    }

    /**
     * Overrides {@link PaginatedGui#open(HumanEntity)} to make it work with the specific scrolls
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        open(player, 1);
    }

    /**
     * Overrides {@link PaginatedGui#open(HumanEntity, int)} to make it work with the specific scrolls
     *
     * @param player   The {@link HumanEntity} to open the GUI to
     * @param openPage The page to open on
     */
    @Override
    public void open(@NotNull final HumanEntity player, final int openPage) {
        if (player.isSleeping()) return;
        getInventory().clear();
        getMutableCurrentPageItems().clear();

        populateGui();

        if (getPageSize() == 0) setPageSize(calculatePageSize());
        if (scrollSize == 0) scrollSize = calculateScrollSize();
        if (openPage > 0 && (openPage * scrollSize + getPageSize() <= getPageItems().size() + scrollSize)) {
            setPageNum(openPage);
        }

        populatePage();

        player.openInventory(getInventory());
    }

    /**
     * Overrides {@link PaginatedGui#updatePage()} to make it work with the specific scrolls
     */
    @Override
    void updatePage() {
        clearPage();
        populatePage();
    }

    /**
     * Fills the page with the items
     */
    private void populatePage() {
        // Adds the paginated items to the page
        for (final GuiItem guiItem : getPage(getPageNum())) {
            if (scrollType == ScrollType.HORIZONTAL) {
                putItemHorizontally(guiItem);
                continue;
            }

            putItemVertically(guiItem);
        }
    }

    /**
     * Calculates the size of each scroll
     *
     * @return The size of he scroll
     */
    private int calculateScrollSize() {
        int counter = 0;

        if (scrollType == ScrollType.VERTICAL) {
            boolean foundCol = false;

            for (int row = 1; row <= getRows(); row++) {
                for (int col = 1; col <= 9; col++) {
                    final int slot = getSlotFromRowCol(row, col);
                    if (getInventory().getItem(slot) == null) {
                        if (!foundCol) foundCol = true;
                        counter++;
                    }
                }

                if (foundCol) return counter;
            }

            return counter;
        }

        boolean foundRow = false;

        for (int col = 1; col <= 9; col++) {
            for (int row = 1; row <= getRows(); row++) {
                final int slot = getSlotFromRowCol(row, col);
                if (getInventory().getItem(slot) == null) {
                    if (!foundRow) foundRow = true;
                    counter++;
                }
            }

            if (foundRow) return counter;
        }

        return counter;
    }

    /**
     * Puts the item in the GUI for horizontal scrolling
     *
     * @param guiItem The gui item
     */
    private void putItemVertically(final GuiItem guiItem) {
        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getGuiItem(slot) != null || getInventory().getItem(slot) != null) continue;
            getMutableCurrentPageItems().put(slot, guiItem);
            getInventory().setItem(slot, guiItem.getItemStack());
            break;
        }
    }

    /**
     * Puts item into the GUI for vertical scrolling
     *
     * @param guiItem The gui item
     */
    private void putItemHorizontally(final GuiItem guiItem) {
        for (int col = 1; col < 10; col++) {
            for (int row = 1; row <= getRows(); row++) {
                final int slot = getSlotFromRowCol(row, col);
                if (getGuiItem(slot) != null || getInventory().getItem(slot) != null) continue;
                getMutableCurrentPageItems().put(slot, guiItem);
                getInventory().setItem(slot, guiItem.getItemStack());
                return;
            }
        }
    }

    /**
     * Gets the items from the current page
     *
     * @param givenPage The page number
     * @return A list with all the items
     */
    private List<GuiItem> getPage(final int givenPage) {
        final int page = givenPage - 1;
        final int pageItemsSize = getPageItems().size();

        final List<GuiItem> guiPage = new ArrayList<>();

        int max = page * scrollSize + getPageSize();
        if (max > pageItemsSize) max = pageItemsSize;

        for (int i = page * scrollSize; i < max; i++) {
            guiPage.add(getPageItems().get(i));
        }

        return guiPage;
    }

}
