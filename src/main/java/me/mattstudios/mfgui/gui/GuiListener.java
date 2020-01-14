package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import static me.mattstudios.mfgui.gui.components.ItemNBT.getNBTTag;

public final class GuiListener implements Listener {

    /**
     * Handles what happens when a player clicks on the GUI
     *
     * @param event The InventoryClickEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onGuiCLick(final InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        // Gui components
        final GUI gui = (GUI) event.getInventory().getHolder();
        final GuiAction<InventoryClickEvent> defaultClick = gui.getDefaultClickAction();

        // Checks weather or not there is a default action and executes it
        if (defaultClick != null) defaultClick.execute(event);

        // Checks if the player is clicking on a non GUI Item
        if (!gui.hasGuiItem(event.getSlot())) return;

        // The clicked GUI Item
        final GuiItem guiItem = gui.getGuiItem(event.getSlot());

        // Checks whether or not the Item is truly a GUI Item
        if (!getNBTTag(event.getCurrentItem(), "mf-gui").equalsIgnoreCase(guiItem.getUuid().toString())) return;

        // Executes the action of the item
        guiItem.getAction().execute(event);

    }

    /**
     * Handles what happens when the GUI is closed
     *
     * @param event The InventoryCloseEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onGuiClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        final GUI gui = (GUI) event.getInventory().getHolder();

        // The GUI action for closing
        final GuiAction<InventoryCloseEvent> closeAction = gui.getCloseGuiAction();

        // Checks if there is or not an action set and executes it
        if (closeAction != null && !gui.isUpdating()) closeAction.execute(event);
    }

    /**
     * Handles what happens when the GUI is closed
     *
     * @param event The InventoryCloseEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onGuiOpen(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        final GUI gui = (GUI) event.getInventory().getHolder();

        // The GUI action for opening
        final GuiAction<InventoryOpenEvent> openAction = gui.getOpenGuiAction();

        // Checks if there is or not an action set and executes it
        if (openAction != null && !gui.isUpdating()) openAction.execute(event);
    }

}
