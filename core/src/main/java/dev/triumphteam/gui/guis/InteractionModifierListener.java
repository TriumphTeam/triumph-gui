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
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Listener that apply default GUI {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier}s to all GUIs
 *
 * @since 3.0.0
 * @author SecretX
 */
public final class InteractionModifierListener implements Listener {

    /**
     * Handles any click on GUIs, applying all {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier} as required
     *
     * @param event The InventoryClickEvent
     * @since 3.0.0
     * @author SecretX
     */
    @EventHandler
    public void onGuiClick(final InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof BaseGui)) return;

        // Gui
        final BaseGui gui = (BaseGui) event.getInventory().getHolder();

        // if player is trying to do a disabled action, cancel it
        if ((!gui.canPlaceItems() && isPlaceItemEvent(event)) || (!gui.canTakeItems() && isTakeItemEvent(event)) || (!gui.canSwapItems() && isSwapItemEvent(event)) || (!gui.canDropItems() && isDropItemEvent(event)) || (!gui.allowsOtherActions() && isOtherEvent(event))) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    /**
     * Handles any item drag on GUIs, applying all {@link dev.triumphteam.gui.components.InteractionModifier InteractionModifier} as required
     *
     * @param event The InventoryDragEvent
     * @since 3.0.0
     * @author SecretX
     */
    @EventHandler
    public void onGuiDrag(final InventoryDragEvent event) {
        if (!(event.getInventory().getHolder() instanceof BaseGui)) return;

        // Gui
        final BaseGui gui = (BaseGui) event.getInventory().getHolder();

        // if players are allowed to place items on the GUI, or player is not dragging on GUI, return
        if(gui.canPlaceItems() || !isDraggingOnGui(event)) return;

        // cancel the interaction
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
    }

    /**
     * Checks if what is happening on the {@link InventoryClickEvent} is take an item from the GUI
     *
     * @param event The InventoryClickEvent
     * @return True if the {@link InventoryClickEvent} is for taking an item from the GUI
     * @since 3.0.0
     * @author SecretX
     */
    private boolean isTakeItemEvent(final InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getInventory();
        final Inventory clickedInventory = event.getClickedInventory();
        final InventoryAction action = event.getAction();

        // magic logic, simplified version of https://paste.helpch.at/tizivomeco.cpp
        if(clickedInventory != null && clickedInventory.getType() == InventoryType.PLAYER || inventory.getType() == InventoryType.PLAYER)
            return false;

        return action == InventoryAction.MOVE_TO_OTHER_INVENTORY || isTakeAction(action);
    }

    /**
     * Checks if what is happening on the {@link InventoryClickEvent} is place an item on the GUI
     *
     * @param event The InventoryClickEvent
     * @return True if the {@link InventoryClickEvent} is for placing an item from the GUI
     * @since 3.0.0
     * @author SecretX
     */
    private boolean isPlaceItemEvent(final InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getInventory();
        final Inventory clickedInventory = event.getClickedInventory();
        final InventoryAction action = event.getAction();

        // shift click on item in player inventory
        if(action == InventoryAction.MOVE_TO_OTHER_INVENTORY
                && clickedInventory != null && clickedInventory.getType() == InventoryType.PLAYER
                && inventory.getType() != clickedInventory.getType())
            return true;

        // normal click on gui empty slot with item on cursor
        return isPlaceAction(action)
            && (clickedInventory == null || clickedInventory.getType() != InventoryType.PLAYER)
            && inventory.getType() != InventoryType.PLAYER;
    }

    /**
     * Checks if what is happening on the {@link InventoryClickEvent} is swap any item with an item from the GUI
     *
     * @param event The InventoryClickEvent
     * @return True if the {@link InventoryClickEvent} is for swapping any item with an item from the GUI
     * @since 3.0.0
     * @author SecretX
     */
    private boolean isSwapItemEvent(final InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getInventory();
        final Inventory clickedInventory = event.getClickedInventory();
        final InventoryAction action = event.getAction();

        return isSwapAction(action)
            && (clickedInventory == null || clickedInventory.getType() != InventoryType.PLAYER)
            && inventory.getType() != InventoryType.PLAYER;
    }

    private boolean isDropItemEvent(final InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getInventory();
        final Inventory clickedInventory = event.getClickedInventory();
        final InventoryAction action = event.getAction();

        return isDropAction(action)
                && (clickedInventory != null || inventory.getType() != InventoryType.PLAYER);
    }

    private boolean isOtherEvent(final InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final Inventory inventory = event.getInventory();
        final Inventory clickedInventory = event.getClickedInventory();
        final InventoryAction action = event.getAction();

        return isOtherAction(action)
                && (clickedInventory != null || inventory.getType() != InventoryType.PLAYER);
    }

    /**
     * Checks if any item is being dragged on the GUI
     *
     * @param event The InventoryDragEvent
     * @return True if the {@link InventoryDragEvent} is for dragging an item inside the GUI
     * @since 3.0.0
     * @author SecretX
     */
    private boolean isDraggingOnGui(final InventoryDragEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");
        final int topSlots = event.getView().getTopInventory().getSize();
        // is dragging on any top inventory slot
        return event.getRawSlots().stream().anyMatch(slot -> slot < topSlots);
    }

    private boolean isTakeAction(final InventoryAction action) {
        Preconditions.checkNotNull(action, "action cannot be null");
        return ITEM_TAKE_ACTIONS.contains(action);
    }

    private boolean isPlaceAction(final InventoryAction action) {
        Preconditions.checkNotNull(action, "action cannot be null");
        return ITEM_PLACE_ACTIONS.contains(action);
    }

    private boolean isSwapAction(final InventoryAction action) {
        Preconditions.checkNotNull(action, "action cannot be null");
        return ITEM_SWAP_ACTIONS.contains(action);
    }

    private boolean isDropAction(final InventoryAction action) {
        Preconditions.checkNotNull(action, "action cannot be null");
        return ITEM_DROP_ACTIONS.contains(action);
    }

    private boolean isOtherAction(final InventoryAction action) {
        Preconditions.checkNotNull(action, "action cannot be null");
        return action == InventoryAction.CLONE_STACK || action == InventoryAction.UNKNOWN;
    }

    /**
     * Holds all the actions that should be considered "take" actions
     */
    private static final Set<InventoryAction> ITEM_TAKE_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_ALL, InventoryAction.COLLECT_TO_CURSOR, InventoryAction.HOTBAR_SWAP, InventoryAction.MOVE_TO_OTHER_INVENTORY));

    /**
     * Holds all the actions that should be considered "place" actions
     */
    private static final Set<InventoryAction> ITEM_PLACE_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME, InventoryAction.PLACE_ALL));

    /**
     * Holds all actions relating to swapping items
     */
    private static final Set<InventoryAction> ITEM_SWAP_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.HOTBAR_SWAP, InventoryAction.SWAP_WITH_CURSOR, InventoryAction.HOTBAR_MOVE_AND_READD));

    /**
     * Holds all actions relating to dropping items
     */
    private static final Set<InventoryAction> ITEM_DROP_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.DROP_ONE_SLOT, InventoryAction.DROP_ALL_SLOT, InventoryAction.DROP_ONE_CURSOR, InventoryAction.DROP_ALL_CURSOR));
}
