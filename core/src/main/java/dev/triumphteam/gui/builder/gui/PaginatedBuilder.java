package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.guis.PaginatedGui;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

/**
 * GUI builder for creating a {@link PaginatedGui}
 */
public class PaginatedBuilder extends BaseGuiBuilder<PaginatedGui, PaginatedBuilder> {

    private int pageSize = 0;

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
        final PaginatedGui gui = new PaginatedGui(getRows(), pageSize, getTitle());

        final Consumer<PaginatedGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
