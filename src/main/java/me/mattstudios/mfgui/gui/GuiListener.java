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

        final GUI gui = (GUI) event.getInventory().getHolder();

        final GuiAction<InventoryClickEvent> defaultClick = gui.getDefaultClickAction();

        if (defaultClick != null) defaultClick.execute(event);

        if (!gui.hasGuiItem(event.getSlot())) return;

        final GuiItem guiItem = gui.getGuiItem(event.getSlot());

        if (!getNBTTag(event.getCurrentItem(), "mf-gui").equalsIgnoreCase(guiItem.getUuid().toString())) return;

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

        final GuiAction<InventoryCloseEvent> closeAction = ((GUI) event.getInventory().getHolder()).getCloseGuiAction();

        if (closeAction != null) closeAction.execute(event);
    }

    /**
     * Handles what happens when the GUI is closed
     *
     * @param event The InventoryCloseEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onGuiOpen(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        final GuiAction<InventoryOpenEvent> openAction = ((GUI) event.getInventory().getHolder()).getOpenGuiAction();

        if (openAction != null) openAction.execute(event);
    }

}
