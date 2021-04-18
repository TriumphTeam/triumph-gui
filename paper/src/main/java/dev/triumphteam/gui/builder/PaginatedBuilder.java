package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.PaperGuiOptions;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link PaginatedGui    } that uses {@link Component} for title
 * For now this is the system I'll use, if anyone can figure a better system PR or let me know!
 * Most of the methods have to be overridden and casted so that the builder can have differences
 * I don't really like how it is since requires a lot of repetition
 * Please send help
 */
public final class PaginatedBuilder extends BaseGuiBuilder<PaginatedGui, Component> {

    private int pageSize = 0;

    /**
     * Sets the rows for the GUI
     *
     * @param rows The amount of rows
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public PaginatedBuilder rows(final int rows) {
        return (PaginatedBuilder) super.rows(rows);
    }

    /**
     * Sets the title of the GUI using {@link Component}
     *
     * @param title The GUI title
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public PaginatedBuilder title(@NotNull final Component title) {
        return (PaginatedBuilder) super.title(title);
    }

    /**
     * Sets the consumer to that'll apply changes to the GUI using a {@link PaginatedGui}
     *
     * @param consumer A {@link Consumer} that passes the built GUI
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public PaginatedBuilder apply(@NotNull final Consumer<PaginatedGui> consumer) {
        return (PaginatedBuilder) super.apply(consumer);
    }

    /**
     * Sets the desirable page size, most of the times this isn't needed
     *
     * @param pageSize The amount of free slots that page items should occupy
     * @return The current builder
     */
    @Contract("_ -> this")
    public PaginatedBuilder pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Creates a new {@link PaginatedGui}
     *
     * @return A new {@link PaginatedGui}
     */
    @Contract(" -> new")
    @Override
    public PaginatedGui create() {
        return new PaginatedGui(new PaperGuiOptions(getTitle(), getRows(), pageSize));
    }

}
