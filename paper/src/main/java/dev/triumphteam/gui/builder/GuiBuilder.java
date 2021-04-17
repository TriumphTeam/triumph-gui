package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Main builder for the library
 */
public final class GuiBuilder {

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
