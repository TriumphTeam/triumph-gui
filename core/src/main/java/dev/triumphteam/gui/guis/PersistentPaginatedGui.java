package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.components.Serializable;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
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
public class PersistentPaginatedGui extends PaginatedGui implements Serializable {

    // Contains all the pages
    private final List<Page> pages = new ArrayList<>();

    // Used for page serialization
    private final YamlConfiguration yamlConfiguration = new YamlConfiguration();

    /**
     * Main constructor of the Persistent GUI
     *
     * @param rows     The rows the GUI should have
     * @param pageSize The pageSize
     * @param title    The GUI's title
     * @param pages    How many pages will be used
     */
    public PersistentPaginatedGui(final int rows, final int pageSize, @NotNull final String title, final int pages) {
        super(rows, pageSize, title);

        if (pages <= 0) {
            this.pages.add(new Page());
            return;
        }

        for (int i = 0; i < pages; i++) {
            this.pages.add(new Page());
        }

    }

    /**
     * Alternative constructor with only title
     *
     * @param title The GUI's title
     */
    public PersistentPaginatedGui(@NotNull final String title) {
        this(1, title);
    }

    /**
     * Alternative constructor with only rows and title
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     */
    public PersistentPaginatedGui(final int rows, @NotNull final String title) {
        this(rows, 0, title, 1);
    }

    /**
     * Alternative constructor with only title and pages
     *
     * @param title The GUI's title
     * @param pages How many pages will be used
     */
    public PersistentPaginatedGui(@NotNull final String title, final int pages) {
        this(1, 0, title, pages);
    }

    /**
     * Alternative constructor with only rows, title and pages
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     * @param pages How many pages will be used
     */
    public PersistentPaginatedGui(final int rows, @NotNull final String title, final int pages) {
        this(rows, 0, title, pages);
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    @NotNull
    public Map<Integer, ItemStack> addItem(@NotNull final ItemStack... items) {
        return addItem(1, items);
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param page  To which page it should be added
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    @NotNull
    public Map<Integer, ItemStack> addItem(final int page, @NotNull final ItemStack... items) {
        int finalPage = page;
        if (page <= 0 || page > pages.size()) finalPage = 1;

        return Collections.unmodifiableMap(getInventory().addItem(items));
    }

    /**
     * TODO this entire class
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    public void open(@NotNull final HumanEntity player) {
        open(player, 1);
    }

    /**
     * Specific open method for the Paginated GUI
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
     * Goes to the next page if possible
     */
    public boolean next() {
        if (getPageNum() + 1 >= pages.size()) return false;

        savePage();

        setPageNum(getPageNum() + 1);
        updatePage();
        return true;
    }

    /**
     * Goes to the previous page if possible
     */
    public boolean previous() {
        if (getPageNum() - 1 < 0) return false;

        savePage();

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
        clearPage();
        populatePage();
    }

    /**
     * Clears the current page
     */
    @Override
    void clearPage() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            final ItemStack itemStack = getInventory().getItem(i);
            if (itemStack == null) continue;
            if (getGuiItems().get(i) != null) continue;

            getInventory().setItem(i, null);
        }
    }

    /**
     * Saves the current page, used by the {@link GuiListener}
     */
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

    /**
     * Encodes the GUI into a list of strings, each string representing a page
     *
     * @return A {@link List} of page items encoded into a string
     */
    @NotNull
    @Override
    public List<String> encodeGui() {
        final int inventorySize = getInventory().getSize();
        final List<String> pageItems = new ArrayList<>();

        for (final Page page : pages) {
            yamlConfiguration.set("inventory", page.getContent(inventorySize));
            pageItems.add(Base64.encodeBase64String(yamlConfiguration.saveToString().getBytes()));
        }

        return pageItems;
    }

    /**
     * Decodes the {@link List} of strings into a usable inventory
     *
     * @param encodedItem The {@link List} to decode
     */
    @Override
    public void decodeGui(@NotNull final List<String> encodedItem) {
        try {
            for (int i = 0; i < pages.size(); i++) {
                final Page page = pages.get(i);
                yamlConfiguration.loadFromString(new String(Base64.decodeBase64(encodedItem.get(i))));
                //noinspection unchecked
                final List<ItemStack> content = (List<ItemStack>) yamlConfiguration.get("inventory");
                if (content == null) continue;
                page.loadPageContent(content, getInventory().getSize());
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private class for holding all the page interactions
     */
    private static class Page {

        // Map that contains all the page items and their slot
        private final Map<Integer, ItemStack> pageItems = new LinkedHashMap<>();

        /**
         * Adds all the items from the page to the {@link Inventory}
         *
         * @param inventory The given {@link Inventory} to use
         */
        private void populatePage(@NotNull final Inventory inventory) {
            // Adds the paginated items to the page
            for (Map.Entry<Integer, ItemStack> entry : pageItems.entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue());
            }
        }

        /**
         * Saves the current page and any modifications that was done to it
         *
         * @param inventory The given {@link Inventory} to use
         * @param guiItems  The gui items map just to check if the current item is or not a gui item, to not add it
         */
        private void savePage(@NotNull final Inventory inventory, @NotNull Map<Integer, GuiItem> guiItems) {
            for (int i = 0; i < inventory.getSize(); i++) {
                final ItemStack itemStack = inventory.getItem(i);

                // Removes the item if it was removed
                if (itemStack == null) {
                    pageItems.remove(i);
                    continue;
                }

                // Skips gui items
                if (guiItems.get(i) != null) continue;

                pageItems.put(i, itemStack);
            }
        }

        /**
         * Turns the map into an array of {@link ItemStack} so it can be serialized
         *
         * @param inventorySize The inventory size
         * @return An array of {@link ItemStack}
         */
        @NotNull
        private ItemStack[] getContent(final int inventorySize) {
            final ItemStack[] content = new ItemStack[inventorySize];
            for (int i = 0; i < inventorySize; i++) {
                content[i] = pageItems.get(i);
            }
            return content;
        }

        /**
         * Loads the list of items into the page
         *
         * @param items         The list of {@link ItemStack}
         * @param inventorySize The inventory size
         */
        private void loadPageContent(@NotNull final List<ItemStack> items, final int inventorySize) {
            // Cleans the page to avoid problems
            pageItems.clear();
            for (int i = 0; i < inventorySize; i++) {
                final ItemStack item = items.get(i);
                if (item == null) continue;

                pageItems.put(i, item);
            }
        }

    }

}
