package me.mattstudios.mfgui.gui.guis;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public final class PaginatedGui extends BaseGui {

    private final List<GuiItem> pageItems = new ArrayList<>();

    private int pageSize;
    private int page = 1;

    public PaginatedGui(@NotNull final Plugin plugin, final int rows, final int pageSize, @NotNull final String title) {
        super(plugin, rows, title);

        this.pageSize = pageSize;

        if (rows < 2) setRows(2);
    }

    public PaginatedGui(@NotNull final Plugin plugin, @NotNull final String title) {
        this(plugin, 2, 9, title);
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
            open(player);
        }

        setUpdating(false);
    }

    /**
     * Overridden open method to add the gui page items
     *
     * @param player The player to open it to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        getInventory().clear();

        for (final Map.Entry<Integer, GuiItem> entry : getGuiItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
        }

        final List<GuiItem> guiPage = getPage(page);

        for (int i = 0; i < guiPage.size(); i++) {
            getInventory().setItem(i, guiPage.get(i).getItemStack());
        }

        player.openInventory(getInventory());

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
        if (page + 1 > getPagesNum()) return -1;
        return page + 1;
    }

    /**
     * Gets the previous page number
     *
     * @return The previous page number or -1 as no previous
     */
    public int getPrevPageNum() {
        if (page - 1 == 0) return -1;
        return page - 1;
    }

    /**
     * Goes to the next page
     */
    public void nextPage() {
        if (page + 1 > getPagesNum()) return;

        page++;
        update();
    }

    /**
     * Goes to the previous page if possible
     */
    public void prevPage() {
        if (page - 1 == 0) return;

        page--;
        update();
    }

    /**
     * Gets the items in the page
     *
     * @param givenPage The page to get
     * @return A list with all the page items
     */
    private List<GuiItem> getPage(final int givenPage) {
        int page = givenPage - 1;

        final List<GuiItem> guiPage = new ArrayList<>();

        int max = ((page * pageSize) + pageSize);
        if (max > this.pageItems.size()) max = this.pageItems.size();

        for (int i = page * pageSize; max > i; i++) {
            guiPage.add(this.pageItems.get(i));
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
}
