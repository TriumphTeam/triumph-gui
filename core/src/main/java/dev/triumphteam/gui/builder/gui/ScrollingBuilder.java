package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.ScrollingGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link ScrollingGui} that uses {@link Component} for title
 * TODO This class needs more work to remove the redundant pageSize since it's the same as the paginated builder
 */
public final class ScrollingBuilder extends BaseGuiBuilder<ScrollingGui, ScrollingBuilder> {

    private ScrollType scrollType;
    private int pageSize = -1;

    /**
     * Main constructor
     *
     * @param scrollType The {@link ScrollType} to default to
     */
    public ScrollingBuilder(@NotNull final ScrollType scrollType) {
        this.scrollType = scrollType;
    }

    /**
     * Sets the {@link ScrollType} to be used
     *
     * @param scrollType Either horizontal or vertical scrolling
     * @return The current builder
     */
    @Contract("_ -> this")
    public ScrollingBuilder scrollType(@NotNull final ScrollType scrollType) {
        this.scrollType = scrollType;
        return this;
    }

    /**
     * Sets the desirable page size, most of the times this isn't needed
     *
     * @param pageSize The amount of free slots that page items should occupy
     * @return The current builder
     */
    @Contract("_ -> this")
    public ScrollingBuilder pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Creates a new {@link ScrollingGui}
     *
     * @return A new {@link ScrollingGui}
     */
    @Contract(" -> new")
    @Override
    public ScrollingGui create() {
        final ScrollingGui gui = new ScrollingGui(getRows(), pageSize, getTitle(), scrollType, getModifiers());

        final Consumer<ScrollingGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
