package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.builder.GuiOptions;
import dev.triumphteam.gui.components.GuiType;
import org.jetbrains.annotations.NotNull;

/**
 * Standard GUI implementation of {@link BaseGui}
 */
public class Gui extends BaseGui {

    /**
     * Main constructor of the GUI
     *
     * @param guiOptions The {@link GuiOptions} implementation
     * @since 3.0.0
     */
    public Gui(@NotNull final GuiOptions guiOptions) {
        super(guiOptions);
    }

    /**
     * Old main constructor for the GUI
     *
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title
     * @deprecated In favor of {@link Gui#Gui(GuiOptions)}
     */
    @Deprecated
    public Gui(final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that defaults to 1 row
     *
     * @param title The GUI's title
     * @deprecated In favor of {@link Gui#Gui(GuiOptions)}
     */
    @Deprecated
    public Gui(@NotNull final String title) {
        super(1, title);
    }

    /**
     * Main constructor that takes a {@link GuiType} instead of rows
     *
     * @param guiType The {@link GuiType} to be used
     * @param title   The GUI's title
     * @deprecated In favor of {@link Gui#Gui(GuiOptions)}
     */
    @Deprecated
    public Gui(@NotNull final GuiType guiType, @NotNull final String title) {
        super(guiType, title);
    }

}
