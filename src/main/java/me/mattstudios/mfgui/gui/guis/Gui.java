package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Standard GUI implementation of {@link BaseGui}
 */
public final class Gui extends BaseGui {

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
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(int, String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Old constructor
     *
     * @param title The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, @NotNull final String title) {
        super(1, title);
    }

    /**
     * Old constructor
     *
     * @param guiType The amount of rows the GUI should have
     * @param title   The GUI's title
     * @deprecated No longer requires the plugin's instance to be passed use {@link #Gui(GuiType, String)} instead
     */
    @Deprecated
    public Gui(@NotNull final Plugin plugin, @NotNull final GuiType guiType, @NotNull final String title) {
        super(guiType, title);
    }

}
