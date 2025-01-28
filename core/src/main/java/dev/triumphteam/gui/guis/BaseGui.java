/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.TriumphGui;
import dev.triumphteam.gui.components.GuiContainer;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.GuiFiller;
import dev.triumphteam.gui.components.util.VersionHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Base class that every GUI extends.
 * Contains all the basics for the GUI to work.
 * Main and simplest implementation of this is {@link Gui}.
 */
@SuppressWarnings("unused")
public abstract class BaseGui implements InventoryHolder {

    // The plugin instance for registering the event and for the close delay.
    private static final Plugin plugin = TriumphGui.getPlugin();

    private static Method GET_SCHEDULER_METHOD = null;
    private static Method EXECUTE_METHOD = null;

    // Registering the listener class.
    static {
        try {
            GET_SCHEDULER_METHOD = Entity.class.getMethod("getScheduler");
            final Class<?> entityScheduler = Class.forName("io.papermc.paper.threadedregions.scheduler.EntityScheduler");
            EXECUTE_METHOD = entityScheduler.getMethod("execute", Plugin.class, Runnable.class, Runnable.class, long.class);
        } catch (NoSuchMethodException | ClassNotFoundException ignored) {
        }

        Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InteractionModifierListener(), plugin);
    }

    // Gui filler.
    private final GuiFiller filler = new GuiFiller(this);
    // Contains all items the GUI will have.
    private final Map<Integer, GuiItem> guiItems;
    // Actions for specific slots.
    private final Map<Integer, GuiAction<InventoryClickEvent>> slotActions;
    // Interaction modifiers.
    private final Set<InteractionModifier> interactionModifiers;

    // GUI control
    private final GuiContainer guiContainer;

    // Main inventory.
    private Inventory inventory;

    // Action to execute when clicking on any item.
    private GuiAction<InventoryClickEvent> defaultClickAction;
    // Action to execute when clicking on the top part of the GUI only.
    private GuiAction<InventoryClickEvent> defaultTopClickAction;
    // Action to execute when clicking on the player Inventory.
    private GuiAction<InventoryClickEvent> playerInventoryAction;
    // Action to execute when dragging the item on the GUI.
    private GuiAction<InventoryDragEvent> dragAction;
    // Action to execute when GUI closes.
    private GuiAction<InventoryCloseEvent> closeGuiAction;
    // Action to execute when GUI opens.
    private GuiAction<InventoryOpenEvent> openGuiAction;
    // Action to execute when clicked outside the GUI.
    private GuiAction<InventoryClickEvent> outsideClickAction;

    // Whether the GUI is updating.
    private boolean updating;

    // Whether should run the actions from the close and open methods.
    private boolean runCloseAction = true;
    private boolean runOpenAction = true;

    public BaseGui(final @NotNull GuiContainer guiContainer, @NotNull final Set<InteractionModifier> interactionModifiers) {
        this.interactionModifiers = safeCopyOf(interactionModifiers);
        this.guiContainer = guiContainer;
        this.inventory = guiContainer.createInventory(this);
        this.slotActions = new LinkedHashMap<>(guiContainer.inventorySize());
        this.guiItems = new LinkedHashMap<>(guiContainer.inventorySize());
    }

    /**
     * Copy a set into an EnumSet, required because {@link EnumSet#copyOf(EnumSet)} throws an exception if the collection passed as argument is empty.
     *
     * @param set The set to be copied.
     * @return An EnumSet with the provided elements from the original set.
     */
    @NotNull
    private Set<InteractionModifier> safeCopyOf(@NotNull final Set<InteractionModifier> set) {
        if (set.isEmpty()) return EnumSet.noneOf(InteractionModifier.class);
        else return EnumSet.copyOf(set);
    }

    /**
     * Gets the GUI title as a {@link Component}.
     *
     * @return The GUI title {@link Component}.
     */
    @NotNull
    public Component title() {
        return guiContainer.title();
    }

    /**
     * Sets the {@link GuiItem} to a specific slot on the GUI.
     *
     * @param slot    The GUI slot.
     * @param guiItem The {@link GuiItem} to add to the slot.
     */
    public void setItem(final int slot, @NotNull final GuiItem guiItem) {
        validateSlot(slot);
        guiItems.put(slot, guiItem);
    }

    /**
     * Removes the given {@link GuiItem} from the GUI.
     *
     * @param item The item to remove.
     */
    public void removeItem(@NotNull final GuiItem item) {
        guiItems.entrySet()
            .stream()
            .filter(it -> it.getValue().equals(item))
            .findFirst()
            .ifPresent(it -> {
                guiItems.remove(it.getKey());
                inventory.remove(it.getValue().getItemStack());
            });
    }

    /**
     * Removes the given {@link ItemStack} from the GUI.
     *
     * @param item The item to remove.
     */
    public void removeItem(@NotNull final ItemStack item) {
        guiItems.entrySet()
            .stream()
            .filter(it -> it.getValue().getItemStack().equals(item))
            .findFirst()
            .ifPresent(it -> {
                guiItems.remove(it.getKey());
                inventory.remove(item);
            });
    }

    /**
     * Removes the {@link GuiItem} in the specific slot.
     *
     * @param slot The GUI slot.
     */
    public void removeItem(final int slot) {
        validateSlot(slot);
        guiItems.remove(slot);
        inventory.setItem(slot, null);
    }

    /**
     * Alternative {@link #removeItem(int)} with cols and rows.
     *
     * @param row The row.
     * @param col The column.
     */
    public void removeItem(final int row, final int col) {
        removeItem(getSlotFromRowCol(row, col));
    }

    /**
     * Alternative {@link #setItem(int, GuiItem)} to set item that takes a {@link List} of slots instead.
     *
     * @param slots   The slots in which the item should go.
     * @param guiItem The {@link GuiItem} to add to the slots.
     */
    public void setItem(@NotNull final List<Integer> slots, @NotNull final GuiItem guiItem) {
        for (final int slot : slots) {
            setItem(slot, guiItem);
        }
    }

    /**
     * Alternative {@link #setItem(int, GuiItem)} to set item that uses <i>ROWS</i> and <i>COLUMNS</i> instead of slots.
     *
     * @param row     The GUI row number.
     * @param col     The GUI column number.
     * @param guiItem The {@link GuiItem} to add to the slot.
     */
    public void setItem(final int row, final int col, @NotNull final GuiItem guiItem) {
        setItem(getSlotFromRowCol(row, col), guiItem);
    }

    /**
     * Adds {@link GuiItem}s to the GUI without specific slot.
     * It'll set the item to the next empty slot available.
     *
     * @param items Varargs for specifying the {@link GuiItem}s.
     */
    public void addItem(@NotNull final GuiItem... items) {
        this.addItem(false, items);
    }

    /**
     * Adds {@link GuiItem}s to the GUI without specific slot.
     * It'll set the item to the next empty slot available.
     *
     * @param items        Varargs for specifying the {@link GuiItem}s.
     * @param expandIfFull If true, expands the gui if it is full
     *                     and there are more items to be added
     */
    public void addItem(final boolean expandIfFull, @NotNull final GuiItem... items) {
        final List<GuiItem> notAddedItems = new ArrayList<>();
        final int rows = guiContainer.rows();
        final GuiType guiType = guiContainer.guiType();

        for (final GuiItem guiItem : items) {
            for (int slot = 0; slot < rows * 9; slot++) {
                if (guiItems.get(slot) != null) {
                    if (slot == rows * 9 - 1) {
                        notAddedItems.add(guiItem);
                    }
                    continue;
                }

                guiItems.put(slot, guiItem);
                break;
            }
        }

        if (!expandIfFull || rows >= 6 || notAddedItems.isEmpty() || guiType != GuiType.CHEST) {
            return;
        }

        if (!(guiContainer instanceof GuiContainer.Chest)) return;
        ((GuiContainer.Chest) guiContainer).rows(guiContainer.rows() + 1);
        this.inventory = guiContainer.createInventory(this);
        this.update();
        this.addItem(true, notAddedItems.toArray(new GuiItem[0]));
    }

    /**
     * Adds a {@link GuiAction} for when clicking on a specific slot.
     * See {@link InventoryClickEvent}.
     *
     * @param slot       The slot that will trigger the {@link GuiAction}.
     * @param slotAction {@link GuiAction} to resolve when clicking on specific slots.
     */
    public void addSlotAction(final int slot, @Nullable final GuiAction<@NotNull InventoryClickEvent> slotAction) {
        validateSlot(slot);
        slotActions.put(slot, slotAction);
    }

    /**
     * Alternative method for {@link #addSlotAction(int, GuiAction)} to add a {@link GuiAction} to a specific slot using <i>ROWS</i> and <i>COLUMNS</i> instead of slots.
     * See {@link InventoryClickEvent}.
     *
     * @param row        The row of the slot.
     * @param col        The column of the slot.
     * @param slotAction {@link GuiAction} to resolve when clicking on the slot.
     */
    public void addSlotAction(final int row, final int col, @Nullable final GuiAction<@NotNull InventoryClickEvent> slotAction) {
        addSlotAction(getSlotFromRowCol(row, col), slotAction);
    }

    /**
     * Gets a specific {@link GuiItem} on the slot.
     *
     * @param slot The slot of the item.
     * @return The {@link GuiItem} on the introduced slot or {@code null} if doesn't exist.
     */
    @Nullable
    public GuiItem getGuiItem(final int slot) {
        return guiItems.get(slot);
    }

    /**
     * Checks whether or not the GUI is updating.
     *
     * @return Whether the GUI is updating or not.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isUpdating() {
        return updating;
    }

    /**
     * Sets the updating status of the GUI.
     *
     * @param updating Sets the GUI to the updating status.
     */
    public void setUpdating(final boolean updating) {
        this.updating = updating;
    }

    /**
     * Opens the GUI for a {@link HumanEntity}.
     *
     * @param player The {@link HumanEntity} to open the GUI to.
     */
    public void open(@NotNull final HumanEntity player) {
        if (player.isSleeping()) return;

        inventory.clear();
        populateGui();
        player.openInventory(inventory);
    }

    /**
     * Closes the GUI with a {@code 2 tick} delay (to prevent items from being taken from the {@link Inventory}).
     *
     * @param player The {@link HumanEntity} to close the GUI to.
     */
    public void close(@NotNull final HumanEntity player) {
        close(player, true);
    }

    /**
     * Closes the GUI with a {@code 2 tick} delay (to prevent items from being taken from the {@link Inventory}).
     *
     * @param player         The {@link HumanEntity} to close the GUI to.
     * @param runCloseAction If should or not run the close action.
     */
    public void close(@NotNull final HumanEntity player, final boolean runCloseAction) {
        final Runnable task = () -> {
            this.runCloseAction = runCloseAction;
            player.closeInventory();
            this.runCloseAction = true;
        };

        if (VersionHelper.IS_FOLIA) {
            if (GET_SCHEDULER_METHOD == null || EXECUTE_METHOD == null) {
                throw new GuiException("Could not find Folia Scheduler methods.");
            }

            try {
                EXECUTE_METHOD.invoke(GET_SCHEDULER_METHOD.invoke(player), plugin, task, null, 2L);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new GuiException("Could not invoke Folia task.", e);
            }
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, task, 2L);
    }

    /**
     * Updates the GUI for all the {@link Inventory} views.
     */
    public void update() {
        inventory.clear();
        populateGui();
        // for (HumanEntity viewer : new ArrayList<>(inventory.getViewers())) ((Player) viewer).updateInventory();
    }

    /**
     * Updates the title of the GUI.
     * <i>This method may cause LAG if used on a loop</i>.
     *
     * @param title The title to set.
     * @return The GUI for easier use when declaring, works like a builder.
     */
    @NotNull
    @Contract("_ -> this")
    public BaseGui updateTitle(@NotNull final Component title) {
        updating = true;

        final List<HumanEntity> viewers = new ArrayList<>(inventory.getViewers());

        guiContainer.title(title); // Update the title.
        inventory = guiContainer.createInventory(this);

        for (final HumanEntity player : viewers) {
            open(player);
        }

        updating = false;
        return this;
    }

    /**
     * Updates the specified item in the GUI at runtime, without creating a new {@link GuiItem}.
     *
     * @param slot      The slot of the item to update.
     * @param itemStack The {@link ItemStack} to replace in the original one in the {@link GuiItem}.
     */
    public void updateItem(final int slot, @NotNull final ItemStack itemStack) {
        final GuiItem guiItem = guiItems.get(slot);

        if (guiItem == null) {
            updateItem(slot, new GuiItem(itemStack));
            return;
        }

        guiItem.setItemStack(itemStack);
        updateItem(slot, guiItem);
    }

    /**
     * Alternative {@link #updateItem(int, ItemStack)} that takes <i>ROWS</i> and <i>COLUMNS</i> instead of slots.
     *
     * @param row       The row of the slot.
     * @param col       The columns of the slot.
     * @param itemStack The {@link ItemStack} to replace in the original one in the {@link GuiItem}.
     */
    public void updateItem(final int row, final int col, @NotNull final ItemStack itemStack) {
        updateItem(getSlotFromRowCol(row, col), itemStack);
    }

    /**
     * Alternative {@link #updateItem(int, ItemStack)} but creates a new {@link GuiItem}.
     *
     * @param slot The slot of the item to update.
     * @param item The {@link GuiItem} to replace in the original.
     */
    public void updateItem(final int slot, @NotNull final GuiItem item) {
        guiItems.put(slot, item);
        inventory.setItem(slot, item.getItemStack());
    }

    /**
     * Alternative {@link #updateItem(int, GuiItem)} that takes <i>ROWS</i> and <i>COLUMNS</i> instead of slots.
     *
     * @param row  The row of the slot.
     * @param col  The columns of the slot.
     * @param item The {@link GuiItem} to replace in the original.
     */
    public void updateItem(final int row, final int col, @NotNull final GuiItem item) {
        updateItem(getSlotFromRowCol(row, col), item);
    }

    /**
     * Disable item placement inside the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableItemPlace() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_PLACE);
        return this;
    }

    /**
     * Disable item retrieval inside the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableItemTake() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_TAKE);
        return this;
    }

    /**
     * Disable item swap inside the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableItemSwap() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_SWAP);
        return this;
    }

    /**
     * Disable item drop inside the GUI
     *
     * @return The BaseGui
     * @since 3.0.3.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableItemDrop() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_DROP);
        return this;
    }

    /**
     * Disable other GUI actions
     * This option pretty much disables creating a clone stack of the item
     *
     * @return The BaseGui
     * @since 3.0.4
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableOtherActions() {
        interactionModifiers.add(InteractionModifier.PREVENT_OTHER_ACTIONS);
        return this;
    }

    /**
     * Disable all the modifications of the GUI, making it immutable by player interaction.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui disableAllInteractions() {
        interactionModifiers.addAll(InteractionModifier.VALUES);
        return this;
    }

    /**
     * Allows item placement inside the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableItemPlace() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_PLACE);
        return this;
    }

    /**
     * Allow items to be taken from the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableItemTake() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_TAKE);
        return this;
    }

    /**
     * Allows item swap inside the GUI.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableItemSwap() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_SWAP);
        return this;
    }

    /**
     * Allows item drop inside the GUI
     *
     * @return The BaseGui
     * @since 3.0.3
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableItemDrop() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_DROP);
        return this;
    }

    /**
     * Enable other GUI actions
     * This option pretty much enables creating a clone stack of the item
     *
     * @return The BaseGui
     * @since 3.0.4
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableOtherActions() {
        interactionModifiers.remove(InteractionModifier.PREVENT_OTHER_ACTIONS);
        return this;
    }

    /**
     * Enable all modifications of the GUI, making it completely mutable by player interaction.
     *
     * @return The BaseGui.
     * @author SecretX.
     * @since 3.0.0.
     */
    @NotNull
    @Contract(" -> this")
    public BaseGui enableAllInteractions() {
        interactionModifiers.clear();
        return this;
    }

    public boolean allInteractionsDisabled() {
        return interactionModifiers.size() == InteractionModifier.VALUES.size();
    }

    /**
     * Check if item placement is allowed inside this GUI.
     *
     * @return True if item placement is allowed for this GUI.
     * @author SecretX.
     * @since 3.0.0.
     */
    public boolean canPlaceItems() {
        return !interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_PLACE);
    }

    /**
     * Check if item retrieval is allowed inside this GUI.
     *
     * @return True if item retrieval is allowed inside this GUI.
     * @author SecretX.
     * @since 3.0.0.
     */
    public boolean canTakeItems() {
        return !interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_TAKE);
    }

    /**
     * Check if item swap is allowed inside this GUI.
     *
     * @return True if item swap is allowed for this GUI.
     * @author SecretX.
     * @since 3.0.0.
     */
    public boolean canSwapItems() {
        return !interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_SWAP);
    }

    /**
     * Check if item drop is allowed inside this GUI
     *
     * @return True if item drop is allowed for this GUI
     * @since 3.0.3
     */
    public boolean canDropItems() {
        return !interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_DROP);
    }

    /**
     * Check if any other actions are allowed in this GUI
     *
     * @return True if other actions are allowed
     * @since 3.0.4
     */
    public boolean allowsOtherActions() {
        return !interactionModifiers.contains(InteractionModifier.PREVENT_OTHER_ACTIONS);
    }

    /**
     * Gets the {@link GuiFiller} that it's used for filling up the GUI in specific ways.
     *
     * @return The {@link GuiFiller}.
     */
    @NotNull
    public GuiFiller getFiller() {
        return filler;
    }

    /**
     * Gets an immutable {@link Map} with all the GUI items.
     *
     * @return The {@link Map} with all the {@link #guiItems}.
     */
    @NotNull
    public Map<@NotNull Integer, @NotNull GuiItem> getGuiItems() {
        return guiItems;
    }

    /**
     * Gets the main {@link Inventory} of this GUI.
     *
     * @return Gets the {@link Inventory} from the holder.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the new inventory of the GUI.
     *
     * @param inventory The new inventory.
     */
    public void setInventory(@NotNull final Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the amount of {@link #guiContainer} rows.
     *
     * @return The {@link #guiContainer }'s rows of the GUI.
     */
    public int getRows() {
        return guiContainer.rows();
    }

    /**
     * Gets the {@link GuiType} in use.
     *
     * @return The {@link GuiType}.
     */
    @NotNull
    public GuiType guiType() {
        return guiContainer.guiType();
    }

    /**
     * Gets the default click resolver.
     */
    @Nullable
    GuiAction<InventoryClickEvent> getDefaultClickAction() {
        return defaultClickAction;
    }

    /**
     * Sets the {@link GuiAction} of a default click on any item.
     * See {@link InventoryClickEvent}.
     *
     * @param defaultClickAction {@link GuiAction} to resolve when any item is clicked.
     */
    public void setDefaultClickAction(@Nullable final GuiAction<@NotNull InventoryClickEvent> defaultClickAction) {
        this.defaultClickAction = defaultClickAction;
    }

    /**
     * Gets the default top click resolver.
     */
    @Nullable
    GuiAction<InventoryClickEvent> getDefaultTopClickAction() {
        return defaultTopClickAction;
    }

    /**
     * Sets the {@link GuiAction} of a default click on any item on the top part of the GUI.
     * Top inventory being for example chests etc, instead of the {@link Player} inventory.
     * See {@link InventoryClickEvent}.
     *
     * @param defaultTopClickAction {@link GuiAction} to resolve when clicking on the top inventory.
     */
    public void setDefaultTopClickAction(@Nullable final GuiAction<@NotNull InventoryClickEvent> defaultTopClickAction) {
        this.defaultTopClickAction = defaultTopClickAction;
    }

    /**
     * Gets the player inventory action.
     */
    @Nullable
    GuiAction<InventoryClickEvent> getPlayerInventoryAction() {
        return playerInventoryAction;
    }

    public void setPlayerInventoryAction(@Nullable final GuiAction<@NotNull InventoryClickEvent> playerInventoryAction) {
        this.playerInventoryAction = playerInventoryAction;
    }

    /**
     * Gets the default drag resolver.
     */
    @Nullable
    GuiAction<InventoryDragEvent> getDragAction() {
        return dragAction;
    }

    /**
     * Sets the {@link GuiAction} of a default drag action.
     * See {@link InventoryDragEvent}.
     *
     * @param dragAction {@link GuiAction} to resolve.
     */
    public void setDragAction(@Nullable final GuiAction<@NotNull InventoryDragEvent> dragAction) {
        this.dragAction = dragAction;
    }

    /**
     * Gets the close gui resolver.
     */
    @Nullable
    GuiAction<InventoryCloseEvent> getCloseGuiAction() {
        return closeGuiAction;
    }

    /**
     * Sets the {@link GuiAction} to run once the inventory is closed.
     * See {@link InventoryCloseEvent}.
     *
     * @param closeGuiAction {@link GuiAction} to resolve when the inventory is closed.
     */
    public void setCloseGuiAction(@Nullable final GuiAction<@NotNull InventoryCloseEvent> closeGuiAction) {
        this.closeGuiAction = closeGuiAction;
    }

    /**
     * Gets the open gui resolver.
     */
    @Nullable
    GuiAction<InventoryOpenEvent> getOpenGuiAction() {
        return openGuiAction;
    }

    /**
     * Sets the {@link GuiAction} to run when the GUI opens.
     * See {@link InventoryOpenEvent}.
     *
     * @param openGuiAction {@link GuiAction} to resolve when opening the inventory.
     */
    public void setOpenGuiAction(@Nullable final GuiAction<@NotNull InventoryOpenEvent> openGuiAction) {
        this.openGuiAction = openGuiAction;
    }

    /**
     * Gets the resolver for the outside click.
     */
    @Nullable
    GuiAction<InventoryClickEvent> getOutsideClickAction() {
        return outsideClickAction;
    }

    /**
     * Sets the {@link GuiAction} to run when clicking on the outside of the inventory.
     * See {@link InventoryClickEvent}.
     *
     * @param outsideClickAction {@link GuiAction} to resolve when clicking outside of the inventory.
     */
    public void setOutsideClickAction(@Nullable final GuiAction<@NotNull InventoryClickEvent> outsideClickAction) {
        this.outsideClickAction = outsideClickAction;
    }

    /**
     * Gets the action for the specified slot.
     *
     * @param slot The slot clicked.
     */
    @Nullable
    GuiAction<InventoryClickEvent> getSlotAction(final int slot) {
        return slotActions.get(slot);
    }

    /**
     * Populates the GUI with it's items.
     */
    void populateGui() {
        for (final Map.Entry<Integer, GuiItem> entry : guiItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }

    boolean shouldRunCloseAction() {
        return runCloseAction;
    }

    boolean shouldRunOpenAction() {
        return runOpenAction;
    }

    /**
     * Gets the slot from the row and column passed.
     *
     * @param row The row.
     * @param col The column.
     * @return The slot needed.
     */
    int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }

    /**
     * Checks if the slot introduces is a valid slot.
     *
     * @param slot The slot to check.
     */
    private void validateSlot(final int slot) {
        final GuiType guiType = guiContainer.guiType();
        final int limit = guiType.getLimit();

        if (guiType == GuiType.CHEST) {
            if (slot < 0 || slot >= guiContainer.rows() * limit) throwInvalidSlot(slot);
            return;
        }

        if (slot < 0 || slot > limit) throwInvalidSlot(slot);
    }

    /**
     * Throws an exception if the slot is invalid.
     *
     * @param slot The specific slot to display in the error message.
     */
    private void throwInvalidSlot(final int slot) {
        if (guiContainer.guiType() == GuiType.CHEST) {
            throw new GuiException("Slot " + slot + " is not valid for the gui type - " + guiContainer.guiType().name() + " and rows - " + guiContainer.rows() + "!");
        }

        throw new GuiException("Slot " + slot + " is not valid for the gui type - " + guiContainer.guiType().name() + "!");
    }

    protected @NotNull GuiContainer guiContainer() {
        return guiContainer;
    }
}
