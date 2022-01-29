/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.guis;

import com.google.common.base.Preconditions;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.AbstractInventoryProperty;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

/**
 * Listener that apply default GUI {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier}s to all GUIs
 *
 * @author SecretX
 * @since 3.0.0
 */
public final class InteractionModifierListener {

    /**
     * Handles any click on GUIs, applying all {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier} as required
     *
     * @param event The ClickInventoryEvent
     * @author SecretX
     * @since 3.0.0
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiClick(final ClickInventoryEvent event) {
        if (!(event.getTargetInventory().getArchetype().getId().contains("triumph:"))) return;

        // Gui
        final BaseGui gui = (BaseGui) event.getTargetInventory().getInventoryProperty(AbstractInventoryProperty.class).orElse(null);
        if (gui == null) {
            return;
        }

        // if player is trying to do a disabled action, cancel it
        if ((!gui.canPlaceItems() && isPlaceItemEvent(event)) || (!gui.canTakeItems() && isTakeItemEvent(event)) || (!gui.canSwapItems() && isSwapItemEvent(event)) || (!gui.canDropItems() && isDropItemEvent(event)) || (!gui.allowsOtherActions() && isOtherEvent(event))) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles any item drag on GUIs, applying all {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier} as required
     *
     * @param event The InventoryDragEvent
     * @author SecretX
     * @since 3.0.0
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiDrag(final ClickInventoryEvent.Drag event) {
        if (!(event.getTargetInventory().getArchetype().getId().contains("triumph:"))) return;

        // Gui
        final BaseGui gui = (BaseGui) event.getTargetInventory().getInventoryProperty(AbstractInventoryProperty.class).orElse(null);
        if (gui == null) {
            return;
        }

        // if players are allowed to place items on the GUI, or player is not dragging on GUI, return
        if (gui.canPlaceItems() || !isDraggingOnGui(event)) return;

        // cancel the interaction
        event.setCancelled(true);
    }

    /**
     * Checks if what is happening on the {@link ClickInventoryEvent} is take an item from the GUI
     *
     * @param event The ClickInventoryEvent
     * @return True if the {@link ClickInventoryEvent} is for taking an item from the GUI
     * @author SecretX
     * @since 3.0.0
     */
    private boolean isTakeItemEvent(final ChangeInventoryEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getTargetInventory();
        final Inventory clickedInventory = event.getTargetInventory();

        // magic logic, simplified version of https://paste.helpch.at/tizivomeco.cpp
        if (clickedInventory != null && clickedInventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.PLAYER) {
            return false;
        }

        for (SlotTransaction slotTransaction : event.getTransactions()) {
            if (slotTransaction.getOriginal().getType() != ItemTypes.NONE && slotTransaction.getFinal().getType() == ItemTypes.NONE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if what is happening on the {@link ClickInventoryEvent} is place an item on the GUI
     *
     * @param event The ClickInventoryEvent
     * @return True if the {@link ClickInventoryEvent} is for placing an item from the GUI
     * @author SecretX
     * @since 3.0.0
     */
    private boolean isPlaceItemEvent(final ChangeInventoryEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getTargetInventory();
        final Inventory clickedInventory = event.getTargetInventory();

        if (clickedInventory != null && clickedInventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.PLAYER) {
            return false;
        }

        // shift click on item in player inventory
        if (event instanceof ChangeInventoryEvent.Transfer
                && clickedInventory != null
                && inventory.getArchetype() != clickedInventory.getArchetype()) {
            return true;
        }

        // normal click on gui empty slot with item on cursor
        for (SlotTransaction slotTransaction : event.getTransactions()) {
            if (slotTransaction.getOriginal().getType() == ItemTypes.NONE && slotTransaction.getFinal().getType() != ItemTypes.NONE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if what is happening on the {@link ClickInventoryEvent} is swap any item with an item from the GUI
     *
     * @param event The ClickInventoryEvent
     * @return True if the {@link ClickInventoryEvent} is for swapping any item with an item from the GUI
     * @author SecretX
     * @since 3.0.0
     */
    private boolean isSwapItemEvent(final ClickInventoryEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getTargetInventory();
        final Inventory clickedInventory = event.getTargetInventory();

        if (clickedInventory != null && clickedInventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.PLAYER) {
            return false;
        }

        for (SlotTransaction slotTransaction : event.getTransactions()) {
            if (slotTransaction.getOriginal().getType() == ItemTypes.NONE && slotTransaction.getFinal().getType() != ItemTypes.NONE) {
                return true;
            }
        }
        return false;
    }

    private boolean isDropItemEvent(final ClickInventoryEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getTargetInventory();
        final Inventory clickedInventory = event.getTargetInventory();

        if (clickedInventory != null && clickedInventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.PLAYER) {
            return false;
        }

        return true;
    }

    private boolean isOtherEvent(final ClickInventoryEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getTargetInventory();
        final Inventory clickedInventory = event.getTargetInventory();

        if (clickedInventory != null && clickedInventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.PLAYER) {
            return false;
        }

        return true;
    }

    /**
     * Checks if any item is being dragged on the GUI
     *
     * @param event The InventoryDragEvent
     * @return True if the {@link InventoryDragEvent} is for dragging an item inside the GUI
     * @author SecretX
     * @since 3.0.0
     */
    private boolean isDraggingOnGui(final ClickInventoryEvent.Drag event) {
        Preconditions.checkNotNull(event, "event cannot be null");
        /* TODO
        final int topSlots = event.getView().getTopInventory().getSize();
        // is dragging on any top inventory slot
        return event.getRawSlots().stream().anyMatch(slot -> slot < topSlots);*/
        return false;
    }
}
