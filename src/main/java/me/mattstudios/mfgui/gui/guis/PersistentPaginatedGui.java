package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiException;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GUI that does not clear it's items once it's closed
 */
@SuppressWarnings("unused")
public final class PersistentPaginatedGui extends PaginatedGui {

    private final List<Page> pages = new ArrayList<>();

    /**
     * Main constructor of the Persistent GUI
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     */
    public PersistentPaginatedGui(final int rows, @NotNull final String title, final int pages) {
        super(rows, title);

        if (pages <= 0) throw new GuiException("Duh");

        for (int i = 0; i < pages; i++) {
            this.pages.add(new Page());
        }

    }

    /**
     * Alternative constructor that does not require rows
     *
     * @param title The GUI's title
     */
    public PersistentPaginatedGui(@NotNull final String title) {
        super(1, title);
    }


    public Map<Integer, ItemStack> addItem(@NotNull final ItemStack... items) {
        return Collections.unmodifiableMap(getInventory().addItem(items));
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
     * Uses
     *
     * @param player   The {@link HumanEntity} to open it to
     * @param openPage The specific page to open at
     */
    public void open(@NotNull final HumanEntity player, final int openPage) {
        if (player.isSleeping()) return;

        if (openPage < pages.size() || openPage > 0) setPageNum(openPage - 1);
        getInventory().clear();

        populateGui();

        if (getPageSize() == 0) setPageSize(calculatePageSize());

        pages.get(getPageNum()).populatePage(getInventory());

        player.openInventory(getInventory());
    }

    /**
     * Goes to the next page
     */
    public boolean next() {
        if (getPageNum() + 1 >= pages.size()) return false;

        pages.get(getPageNum()).savePage(getInventory(), getGuiItems());

        setPageNum(getPageNum() + 1);
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     */
    public boolean previous() {
        if (getPageNum() - 1 < 0) return false;

        pages.get(getPageNum()).savePage(getInventory(), getGuiItems());

        setPageNum(getPageNum() - 1);
        updatePage();
        return true;
    }

    /**
     * Gets the current page number
     *
     * @return The current page number
     */
    @Override
    public int getCurrentPageNum() {
        return getPageNum() + 1;
    }

    /**
     * Updates the page content
     */
    @Override
    void updatePage() {
        Bukkit.broadcastMessage(String.valueOf(getCurrentPageNum()));
        clearPage();
        populatePage();
    }

    void clearPage() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            final ItemStack itemStack = getInventory().getItem(i);
            if (itemStack == null) continue;
            if (getGuiItems().get(i) != null) continue;

            getInventory().setItem(i, null);
        }
    }

    void savePage() {
        pages.get(getPageNum()).savePage(getInventory(), getGuiItems());
    }

    /**
     * Populates the inventory with the page items
     */
    private void populatePage() {
        // Adds the paginated items to the page
        pages.get(getPageNum()).populatePage(getInventory());
    }

    private static class Page {

        private final Map<Integer, ItemStack> items = new LinkedHashMap<>();

        private void populatePage(@NotNull final Inventory inventory) {
            // Adds the paginated items to the page
            for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue());
            }
        }

        private void savePage(@NotNull final Inventory inventory, @NotNull Map<Integer, GuiItem> guiItems) {

            for (int i = 0; i < inventory.getSize(); i++) {
                final ItemStack itemStack = inventory.getItem(i);

                if (itemStack == null) {
                    items.remove(i);
                    continue;
                }

                if (guiItems.get(i) != null) continue;

                items.put(i, itemStack);
            }

        }

    }

}
