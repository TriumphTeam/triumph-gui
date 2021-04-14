package dev.triumphteam.gui.components;

import org.bukkit.event.inventory.InventoryEvent;

@FunctionalInterface
public interface GuiAction<T extends InventoryEvent> {

    /**
     * Executes the task passed to it
     *
     * @param event Inventory action
     */
    void execute(final T event);
}
