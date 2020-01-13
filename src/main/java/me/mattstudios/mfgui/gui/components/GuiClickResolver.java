package me.mattstudios.mfgui.gui.components;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface GuiClickResolver {

    /**
     * Resolver for the item click
     *
     * @param event Inventory click event of the clicked item
     */
    void resolve(final InventoryClickEvent event);

}
