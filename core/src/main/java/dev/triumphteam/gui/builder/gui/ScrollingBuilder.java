package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.ScrollingGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link ScrollingGui} that uses {@link Component} for title
 * For now this is the system I'll use, if anyone can figure a better system PR or let me know!
 * Most of the methods have to be overridden and casted so that the builder can have differences
 * I don't really like how it is since requires a lot of repetition
 * Please send help
 */
public final class ScrollingBuilder extends BaseGuiBuilder<ScrollingGui> {

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
     * Sets the rows for the GUI
     *
     * @param rows The amount of rows
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public ScrollingBuilder rows(final int rows) {
        return (ScrollingBuilder) super.rows(rows);
    }

    /**
     * Sets the title of the GUI using {@link Component}
     *
     * @param title The GUI title
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public ScrollingBuilder title(@NotNull final Component title) {
        return (ScrollingBuilder) super.title(title);
    }

    /**
     * Sets the consumer to that'll apply changes to the GUI using a {@link ScrollingGui}
     *
     * @param consumer A {@link Consumer} that passes the built GUI
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public ScrollingBuilder apply(@NotNull final Consumer<ScrollingGui> consumer) {
        return (ScrollingBuilder) super.apply(consumer);
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
        final ScrollingGui gui = new ScrollingGui(getRows(), pageSize, getTitle(), scrollType);

        final Consumer<ScrollingGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
