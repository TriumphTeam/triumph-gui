package dev.triumphteam.gui.event;

import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * This event is triggered upon a page change (previous or next page) within a {@link PaginatedGui}
 */
public class GuiPageChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Map<Integer, GuiItem> previousPageItems;
    private final Map<Integer, GuiItem> currentPageItems;
    private final PaginatedGui gui;

    /**
     * Constructor for a page change event
     * @param previousPageItems the items stored from the previous page
     * @param currentPageItems - the items stored in the page it has changed to
     * @param gui the gui relating to the event
     */
    public GuiPageChangeEvent(@NotNull final Map<Integer, GuiItem> previousPageItems,
                              @NotNull final Map<Integer, GuiItem> currentPageItems,
                              @NotNull final PaginatedGui gui) {
        this.previousPageItems = previousPageItems;
        this.currentPageItems = currentPageItems;
        this.gui = gui;
    }

    /**
     * Get the items stored from the previous page
     * @return the previous page items
     */
    @NotNull
    public Map<Integer, GuiItem> getPreviousPageItems() {
        return previousPageItems;
    }

    /**
     * get the items stored in the page it has changed to
     * @return the current page items
     */
    @NotNull
    public Map<Integer, GuiItem> getCurrentPageItems() {
        return currentPageItems;
    }

    /**
     * Get the gui
     * @return the gui
     */
    @NotNull
    public PaginatedGui getGui() {
        return gui;
    }

    // TODO find a way to get the player who clicked / triggered the event
    /**
     * Get a list of all players viewing this gui
     * @return the players viewing the gui
     */
    @NotNull
    public List<HumanEntity> getViewers() {
        return gui.getInventory().getViewers();
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
