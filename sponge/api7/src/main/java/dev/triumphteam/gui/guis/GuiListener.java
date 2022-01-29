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

import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.components.util.ItemNbt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drop.Outside;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.common.item.inventory.custom.CustomContainer;

public final class GuiListener {

    /**
     * Handles what happens when a player clicks on the GUI
     *
     * @param event The ClickInventoryEvent
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiClick(final ClickInventoryEvent event) {
        if (!(event.getTargetInventory() instanceof CustomContainer) || !((CustomContainer) event.getTargetInventory()).inv.getArchetype().getId().contains("triumph:")) {
            return;
        }

        // Gui
        final BaseGui gui = (BaseGui) ((CustomContainer) event.getTargetInventory()).inv.getProperties().get("triumph");
        if (gui == null) {
            return;
        }

        if (event instanceof ClickInventoryEvent.Drop.Outside) {
            // Executes the outside click action
            final GuiAction<ClickInventoryEvent.Drop.Outside> outsideClickAction = gui.getOutsideClickAction();
            if (outsideClickAction != null && event.getTargetInventory() == null) {
                outsideClickAction.execute((Outside) event);
                return;
            }
            return;
        }

        // Default click action and checks weather or not there is a default action and executes it
        final GuiAction<ClickInventoryEvent> defaultTopClick = gui.getDefaultTopClickAction();
        if (defaultTopClick != null && event.getTargetInventory().getArchetype() != InventoryArchetypes.PLAYER) {
            defaultTopClick.execute(event);
        }

        // Default click action and checks weather or not there is a default action and executes it
        final GuiAction<ClickInventoryEvent> playerInventoryClick = gui.getPlayerInventoryAction();
        if (playerInventoryClick != null && event.getTargetInventory().getArchetype() == InventoryArchetypes.PLAYER) {
            playerInventoryClick.execute(event);
        }

        // Default click action and checks weather or not there is a default action and executes it
        final GuiAction<ClickInventoryEvent> defaultClick = gui.getDefaultClickAction();
        if (defaultClick != null) {
            defaultClick.execute(event);
        }

        // Slot action and checks weather or not there is a slot action and executes it
        final Slot slot = event.getSlot().orElse(null);
        if (slot != null) {
            final int slotIndex = slot.getInventoryProperty(SlotIndex.class).get().getValue();
            final GuiAction<ClickInventoryEvent> slotAction = gui.getSlotAction(slotIndex);
            if (slotAction != null && event.getTargetInventory().getArchetype() != InventoryArchetypes.PLAYER) {
                slotAction.execute(event);
            }

            // Checks whether it's a paginated gui or not
            GuiItem guiItem = null;
            if (gui instanceof PaginatedGui) {
                final PaginatedGui paginatedGui = (PaginatedGui) gui;

                // Gets the gui item from the added items or the page items
                guiItem = paginatedGui.getGuiItem(slotIndex);
                if (guiItem == null) guiItem = paginatedGui.getPageItem(slotIndex);

            } else {
                // The clicked GUI Item
                guiItem = gui.getGuiItem(slotIndex);
            }
            if (guiItem == null) {
                return;
            }
            final ItemStack itemStack = guiItem.getItemStack();

            if (itemStack == null) {
                return;
            }

            if (!isGuiItem(itemStack, guiItem)) return;

            // Executes the action of the item
            final GuiAction<ClickInventoryEvent> itemAction = guiItem.getAction();
            if (itemAction != null) itemAction.execute(event);
        }
    }

    /**
     * Handles what happens when a player clicks on the GUI
     *
     * @param event The ClickInventoryEvent
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiDrag(final ClickInventoryEvent.Drag event) {
        if (!(event.getTargetInventory() instanceof CustomContainer) || !((CustomContainer) event.getTargetInventory()).inv.getArchetype().getId().contains("triumph:")) {
            return;
        }
        // Gui
        final BaseGui gui = (BaseGui) ((CustomContainer) event.getTargetInventory()).inv.getProperties().get("triumph");
        if (gui == null) {
            return;
        }

        // Default click action and checks weather or not there is a default action and executes it
        final GuiAction<ClickInventoryEvent.Drag> dragAction = gui.getDragAction();
        if (dragAction != null) dragAction.execute(event);
    }

    /**
     * Handles what happens when the GUI is closed
     *
     * @param event The InteractInventoryEvent.Close
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiClose(final InteractInventoryEvent.Close event) {
        if (!(event.getTargetInventory() instanceof CustomContainer) || !((CustomContainer) event.getTargetInventory()).inv.getArchetype().getId().contains("triumph:")) {
            return;
        }
        // GUI
        final BaseGui gui = (BaseGui) ((CustomContainer) event.getTargetInventory()).inv.getProperties().get("triumph");
        if (gui == null) {
            return;
        }

        // The GUI action for closing
        final GuiAction<InteractInventoryEvent.Close> closeAction = gui.getCloseGuiAction();

        // Checks if there is or not an action set and executes it
        if (closeAction != null && !gui.isUpdating() && gui.shouldRunCloseAction()) closeAction.execute(event);
    }

    /**
     * Handles what happens when the GUI is opened
     *
     * @param event The InteractInventoryEvent.Open
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onGuiOpen(final InteractInventoryEvent.Open event) {
        if (!(event.getTargetInventory() instanceof CustomContainer) || !((CustomContainer) event.getTargetInventory()).inv.getArchetype().getId().contains("triumph:")) {
            return;
        }
        // GUI
        final BaseGui gui = (BaseGui) ((CustomContainer) event.getTargetInventory()).inv.getProperties().get("triumph");
        if (gui == null) {
            return;
        }

        // The GUI action for opening
        final GuiAction<InteractInventoryEvent.Open> openAction = gui.getOpenGuiAction();

        // Checks if there is or not an action set and executes it
        if (openAction != null && !gui.isUpdating()) openAction.execute(event);
    }

    /**
     * Checks if the item is or not a GUI item
     *
     * @param currentItem The current item clicked
     * @param guiItem     The GUI item in the slot
     * @return Whether it is or not a GUI item
     */
    private boolean isGuiItem(@Nullable final ItemStack currentItem, @Nullable final GuiItem guiItem) {
        if (currentItem == null || guiItem == null) return false;
        // Checks whether the Item is truly a GUI Item
        final String nbt = ItemNbt.getString(currentItem, "mf-gui");
        if (nbt == null) return false;
        return nbt.equals(guiItem.getUuid().toString());
    }

}
