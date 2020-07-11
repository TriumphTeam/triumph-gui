package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.ScrollType;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ScrollingGui extends PaginatedGui {

    private final ScrollType scrollType;
    private int scrollSize = 0;

    public ScrollingGui(@NotNull final Plugin plugin, final int rows, final int pageSize, @NotNull final String title, @NotNull final ScrollType scrollType) {
        super(plugin, rows, pageSize, title);

        this.scrollType = scrollType;
    }

    public ScrollingGui(@NotNull final Plugin plugin, final int rows, final int pageSize, @NotNull final String title) {
        this(plugin, rows, pageSize, title, ScrollType.VERTICAL);
    }

    public ScrollingGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        this(plugin, rows, 0, title, ScrollType.VERTICAL);
    }

    public ScrollingGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title, @NotNull final ScrollType scrollType) {
        this(plugin, rows, 0, title, scrollType);
    }

    public ScrollingGui(@NotNull final Plugin plugin, @NotNull final String title) {
        this(plugin, 2, title);
    }

    public ScrollingGui(@NotNull final Plugin plugin, @NotNull final String title, @NotNull final ScrollType scrollType) {
        this(plugin, 2, title, scrollType);
    }

    /**
     * Goes to the next page
     */
    @Override
    public boolean next() {
        if (getPage() * scrollSize + getPageSize() > getPageItems().size() + scrollSize) return false;

        setPage(getPage() + 1);
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     */
    @Override
    public boolean previous() {
        if (getPage() - 1 == 0) return false;

        setPage(getPage() - 1);
        updatePage();
        return true;
    }

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
        if (openPage <= getMaximumScrolls() || openPage > 0) setPage(openPage);

        getInventory().clear();
        getCurrentPageItems().clear();

        populateGui();

        if (getPageSize() == 0) setPageSize(calculatePageSize());
        if (scrollSize == 0) scrollSize = calculateScrollSize();
        Bukkit.broadcastMessage(String.valueOf(scrollSize));

        populatePage();

        player.openInventory(getInventory());
    }

    /**
     * Updates the page content
     *
     * @since 2.2.5
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
        for (final GuiItem guiItem : getPage(getPage())) {
            if (scrollType == ScrollType.HORIZONTAL) {
                putItemHorizontally(guiItem);
                continue;
            }

            putItemVertically(guiItem);
        }
    }

    private int calculateScrollSize() {
        if (scrollType == ScrollType.VERTICAL) {
            int counter = 0;
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

        return 0;
    }

    /**
     * Puts the item in the GUI for horizontal scrolling
     *
     * @param guiItem The gui item
     */
    private void putItemVertically(final GuiItem guiItem) {
        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getInventory().getItem(slot) != null) continue;
            getCurrentPageItems().put(slot, guiItem);
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
                if (getInventory().getItem(slot) != null) continue;

                getCurrentPageItems().put(slot, guiItem);
                getInventory().setItem(slot, guiItem.getItemStack());
                return;
            }
        }
    }

    /**
     * Gets the maximum amount of scrolls possible in the GUI
     *
     * @return The maximum amount of scrolls
     */
    private int getMaximumScrolls() {
        return 0;
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
