package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.PaperGuiOptions;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link Gui} that uses {@link Component} for title
 * For now this is the system I'll use, if anyone can figure a better system PR or let me know!
 * Most of the methods have to be overridden and casted so that the builder can have differences
 * I don't really like how it is since requires a lot of repetition
 * Please send help
 */
public final class SimpleBuilder extends BaseGuiBuilder<Gui, Component> {

    private GuiType guiType;

    /**
     * Main constructor
     *
     * @param guiType The {@link GuiType} to default to
     */
    public SimpleBuilder(@NotNull final GuiType guiType) {
        this.guiType = guiType;
    }

    /**
     * Sets the rows for the GUI
     *
     * @param rows The amount of rows
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public SimpleBuilder rows(final int rows) {
        return (SimpleBuilder) super.rows(rows);
    }

    /**
     * Sets the title of the GUI using {@link Component}
     *
     * @param title The GUI title
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public SimpleBuilder title(@NotNull final Component title) {
        return (SimpleBuilder) super.title(title);
    }

    /**
     * Sets the consumer to that'll apply changes to the GUI using a {@link Gui}
     *
     * @param consumer A {@link Consumer} that passes the built GUI
     * @return The current builder
     */
    @Contract("_ -> this")
    @Override
    public SimpleBuilder apply(@NotNull final Consumer<Gui> consumer) {
        return (SimpleBuilder) super.apply(consumer);
    }

    /**
     * Sets the {@link GuiType} to use on the GUI
     * This method is unique to the simple GUI
     *
     * @param guiType The {@link GuiType}
     * @return The current builder
     */
    @Contract("_ -> this")
    public SimpleBuilder type(@NotNull final GuiType guiType) {
        this.guiType = guiType;
        return this;
    }

    /**
     * Creates a new {@link Gui}
     *
     * @return A new {@link Gui}
     */
    @Contract(" -> new")
    @Override
    public Gui create() {
        return new Gui(new PaperGuiOptions(getTitle(), getRows(), guiType, ScrollType.HORIZONTAL));
    }

}
