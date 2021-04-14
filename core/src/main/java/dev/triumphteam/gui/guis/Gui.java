package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.components.GuiType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Standard GUI implementation of {@link BaseGui}
 */
public class Gui extends BaseGui {

    /**
     * Main constructor for the GUI
     *
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title
     */
    public Gui(final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that defaults to 1 row
     *
     * @param title The GUI's title
     */
    public Gui(@NotNull final String title) {
        super(1, title);
    }

    /**
     * Main constructor that takes a {@link GuiType} instead of rows
     *
     * @param guiType The {@link GuiType} to be used
     * @param title   The GUI's title
     */
    public Gui(@NotNull final GuiType guiType, @NotNull final String title) {
        super(guiType, title);
    }

    /**
     * Old constructor
     *
     * @param plugin The plugin's instance
     * @param rows   The amount of rows the GUI should have
     * @param title  The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(int, String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Old constructor
     *
     * @param plugin The plugin's instances
     * @param title  The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, @NotNull final String title) {
        super(1, title);
    }

    /**
     * Old constructor
     *
     * @param plugin  The plugin's Instances
     * @param guiType The amount of rows the GUI should have
     * @param title   The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(GuiType, String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, @NotNull final GuiType guiType, @NotNull final String title) {
        super(guiType, title);
    }

    /**
     * Opens the GUI to a player
     *
     * @param player The {@link HumanEntity} to open the GUI to
     */
    public void open(final @NotNull HumanEntity player) {
        if (!openCheck(player)) return;
        getInventory().clear();
        populateGui();
        player.openInventory(getInventory());
    }

}
