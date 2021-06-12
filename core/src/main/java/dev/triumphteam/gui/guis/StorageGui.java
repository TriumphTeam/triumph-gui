package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.InteractionModifier;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * GUI that does not clear it's items once it's closed
 */
@SuppressWarnings("unused")
public class StorageGui extends BaseGui {

    /**
     * Main constructor for the StorageGui, now with {@link Component} support
     *
     * @param rows                 The amount of rows the GUI should have
     * @param title                The GUI's title using {@link Component}
     * @param interactionModifiers A set containing the {@link InteractionModifier} this GUI should use
     * @since 3.0.0
     */
    public StorageGui(final int rows, @NotNull final Component title, @NotNull final Set<InteractionModifier> interactionModifiers) {
        super(rows, title, interactionModifiers);
    }

    /**
     * Alternative constructor to provide a way to create GUIs that don't need any {@link InteractionModifier}
     *
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public StorageGui(final int rows, @NotNull final Component title) {
        this(rows, title, Collections.emptySet());
    }

    /**
     * Alternative constructor that defaults to 1 row
     *
     * @param title The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public StorageGui(@NotNull final Component title) {
        super(1, title, Collections.emptySet());
    }

    /**
     * Main constructor that takes a {@link GuiType} instead of rows
     *
     * @param guiType The {@link GuiType} to be used
     * @param title   The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public StorageGui(@NotNull final GuiType guiType, @NotNull final Component title) {
        super(guiType, title, Collections.emptySet());
    }

    /**
     * Alternative constructor that takes both a {@link GuiType} and a set of {@link InteractionModifier}
     *
     * @param guiType              The {@link GuiType} to be used
     * @param title                The GUI's title using {@link Component}
     * @param interactionModifiers A set containing the {@link InteractionModifier} this GUI should use
     * @since 3.0.0
     */
    public StorageGui(@NotNull final GuiType guiType, @NotNull final Component title, @NotNull final Set<InteractionModifier> interactionModifiers) {
        super(guiType, title, interactionModifiers);
    }

    /**
     * Main constructor of the Persistent GUI
     *
     * @param rows  The rows the GUI should have
     * @param title The GUI's title
     */
    @Deprecated
    public StorageGui(final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that does not require rows
     *
     * @param title The GUI's title
     */
    @Deprecated
    public StorageGui(@NotNull final String title) {
        super(1, title);
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    public Map<Integer, ItemStack> addItem(@NotNull final ItemStack... items) {
        return Collections.unmodifiableMap(getInventory().addItem(items));
    }

    /**
     * Adds {@link ItemStack} to the inventory straight, not the GUI
     *
     * @param items Varargs with {@link ItemStack}s
     * @return An immutable {@link Map} with the left overs
     */
    public Map<Integer, ItemStack> addItem(@NotNull final List<ItemStack> items) {
        return addItem(items.toArray(new ItemStack[0]));
    }

    /**
     * Overridden {@link BaseGui#open(HumanEntity)} to prevent
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    @Override
    public void open(@NotNull final HumanEntity player) {
        if (player.isSleeping()) return;
        populateGui();
        player.openInventory(getInventory());
    }

}
