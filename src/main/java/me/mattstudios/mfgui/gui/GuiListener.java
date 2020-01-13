package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class GuiListener implements Listener {

    @EventHandler
    public void on(final InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getInventory().getHolder();

        GuiClickResolver defaultClick = gui.getDefaultClick();

        if (defaultClick != null) {
            defaultClick.resolve(event);
        }

    }

}
