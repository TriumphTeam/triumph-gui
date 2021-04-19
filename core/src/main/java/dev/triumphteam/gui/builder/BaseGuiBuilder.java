package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.guis.BaseGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The base for all the GUI builders this is due to some limitations
 * where some builders will have unique features based on the GUI type
 *
 * @param <G> The Type of {@link BaseGui}
 */
public abstract class BaseGuiBuilder<G extends BaseGui> {

    private Component title = null;
    private int rows = 1;

    private Consumer<G> consumer;

    /**
     * Sets the rows for the GUI
     * This will only work on CHEST {@link dev.triumphteam.gui.components.GuiType}
     *
     * @param rows The amount of rows
     * @return The builder
     */
    @Contract("_ -> this")
    public BaseGuiBuilder<G> rows(final int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * Sets the title for the GUI
     * This will be either a Component or a String
     *
     * @param title The GUI title
     * @return The builder
     */
    @Contract("_ -> this")
    public BaseGuiBuilder<G> title(@NotNull final Component title) {
        this.title = title;
        return this;
    }

    /**
     * Applies anything to the GUI once it's created
     * Can be pretty useful for setting up small things like default actions
     *
     * @param consumer A {@link Consumer} that passes the built GUI
     * @return The builder
     */
    @Contract("_ -> this")
    public BaseGuiBuilder<G> apply(@NotNull final Consumer<G> consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * Creates the given GuiBase
     * Has to be abstract because each GUI are different
     *
     * @return The new {@link BaseGui}
     */
    @Contract(" -> new")
    public abstract G create();

    /**
     * Getter for the title
     *
     * @return The current title
     */
    @NotNull
    protected Component getTitle() {
        if (title == null) {
            // TODO better message
            throw new GuiException("GUI title is missing!");
        }

        return title;
    }

    /**
     * Getter for the rows
     *
     * @return The amount of rows
     */
    protected int getRows() {
        return rows;
    }

    /**
     * Getter for the consumer
     *
     * @return The consumer
     */
    @Nullable
    protected Consumer<G> getConsumer() {
        return consumer;
    }

}
