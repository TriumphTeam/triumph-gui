package dev.triumphteam.gui.guis;

import dev.triumphteam.gui.builder.PaginatedBuilder;
import dev.triumphteam.gui.builder.ScrollingBuilder;
import dev.triumphteam.gui.builder.SimpleBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Standard GUI implementation of {@link BaseGui}
 */
public class Gui extends BaseGui {

    /**
     * Main constructor for the GUI, now with {@link Component} support
     *
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public Gui(final int rows, @NotNull final Component title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that defaults to 1 row
     *
     * @param title The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public Gui(@NotNull final Component title) {
        super(1, title);
    }

    /**
     * Main constructor that takes a {@link GuiType} instead of rows
     *
     * @param guiType The {@link GuiType} to be used
     * @param title   The GUI's title using {@link Component}
     * @since 3.0.0
     */
    public Gui(@NotNull final GuiType guiType, @NotNull final Component title) {
        super(guiType, title);
    }

    /**
     * Old main constructor for the GUI
     *
     * @param rows  The amount of rows the GUI should have
     * @param title The GUI's title
     * @deprecated In favor of {@link Gui#Gui(int, Component)}
     */
    @Deprecated
    public Gui(final int rows, @NotNull final String title) {
        super(rows, title);
    }

    /**
     * Alternative constructor that defaults to 1 row
     *
     * @param title The GUI's title
     * @deprecated In favor of {@link Gui#Gui(Component)}
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
     * @deprecated In favor of {@link Gui#Gui(GuiType, Component)}
     */
    @Deprecated
    public Gui(@NotNull final GuiType guiType, @NotNull final String title) {
        super(guiType, title);
    }

    /**
     * Creates a {@link SimpleBuilder} to build a {@link dev.triumphteam.gui.guis.Gui}
     *
     * @param type The {@link GuiType} to be used
     * @return A {@link SimpleBuilder}
     */
    @Contract("_ -> new")
    public static SimpleBuilder gui(@NotNull final GuiType type) {
        return new SimpleBuilder(type);
    }

    /**
     * Creates a {@link SimpleBuilder} with CHEST as the {@link GuiType}
     *
     * @return A CHEST {@link SimpleBuilder}
     */
    @Contract(" -> new")
    public static SimpleBuilder gui() {
        return gui(GuiType.CHEST);
    }

    /**
     * Creates a {@link PaginatedBuilder} to build a {@link dev.triumphteam.gui.guis.PaginatedGui}
     *
     * @return A {@link PaginatedBuilder}
     */
    @Contract(" -> new")
    public static PaginatedBuilder paginated() {
        return new PaginatedBuilder();
    }

    /**
     * Creates a {@link ScrollingBuilder} to build a {@link dev.triumphteam.gui.guis.ScrollingGui}
     *
     * @param scrollType The {@link ScrollType} to be used by the GUI
     * @return A {@link ScrollingBuilder}
     */
    @Contract("_ -> new")
    public static ScrollingBuilder scrolling(@NotNull final ScrollType scrollType) {
        return new ScrollingBuilder(scrollType);
    }

    /**
     * Creates a {@link ScrollingBuilder} with VERTICAL as the {@link ScrollType}
     *
     * @return A vertical {@link SimpleBuilder}
     */
    @Contract(" -> new")
    public static ScrollingBuilder scrolling() {
        return scrolling(ScrollType.VERTICAL);
    }

}
