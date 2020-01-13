package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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

        final GuiClickResolver defaultClick = gui.getDefaultClick();

        if (defaultClick != null) defaultClick.resolve(event);

        if (!gui.hasGuiItem(event.getSlot())) return;

        final GuiItem guiItem = gui.getGuiItem(event.getSlot());

        if (!getNBTTag(event.getCurrentItem(), "mf-gui").equalsIgnoreCase(guiItem.getUuid().toString())) return;

        guiItem.getAction().resolve(event);

    }

    @EventHandler(ignoreCancelled = true)
    public void onGuiClose(final InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

    }
}
