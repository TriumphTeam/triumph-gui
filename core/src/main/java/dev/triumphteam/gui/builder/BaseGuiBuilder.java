/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.GuiView;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.GuiComponentProducer;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponent;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentBuilder;
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.settings.GuiSettings;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main builder for all GUIs.
 * Container the main base methods that are shared between all implementations.
 *
 * @param <B> The implementation of this {@link BaseGuiBuilder}.
 * @param <P> The player type.
 * @param <G> The {@link BaseGui} type.
 * @param <I> The item type.
 */
@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class BaseGuiBuilder<B extends BaseGuiBuilder<B, P, G, I>, P, G extends BaseGui<P>, I> {

    private final GuiSettings<P, I, ?> guiSettings;
    private final GuiContainerType containerType;
    private final List<GuiComponent<P, I>> components = new ArrayList<>();

    private ClickHandler<P> clickHandler = null;
    private GuiComponentRenderer<P, I> componentRenderer = null;
    private Component title = null;
    private long spamPreventionDuration = -1;

    public BaseGuiBuilder(
        final GuiSettings<P, I, ?> guiSettings,
        final @NotNull GuiContainerType containerType
    ) {
        this.guiSettings = guiSettings;
        this.containerType = containerType;
    }

    /**
     * Sets the title of the {@link GuiView}.
     *
     * @param title The title {@link Component}.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B title(final @NotNull Component title) {
        this.title = title;
        return (B) this;
    }

    /**
     * Sets the {@link ClickHandler} to be used for all {@link GuiComponent} unless they provide their own.
     *
     * @param clickHandler The default {@link ClickHandler}.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B clickHandler(final @NotNull ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
        return (B) this;
    }

    /**
     * Sets the {@link GuiComponentRenderer} to be used by the {@link BaseGui}.
     *
     * @param componentRenderer The {@link GuiComponentRenderer} to use.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B componentRenderer(final @NotNull GuiComponentRenderer<P, I> componentRenderer) {
        this.componentRenderer = componentRenderer;
        return (B) this;
    }

    /**
     * Sets how long the spam prevention should be, in {@link TimeUnit#MILLISECONDS}.
     * Set it to {@code 0} to disable it altogether.
     *
     * @param spamPreventionDuration How long the spam prevention should last.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B spamPreventionDuration(final long spamPreventionDuration) {
        if (spamPreventionDuration < 0) {
            throw new TriumphGuiException("Spam prevention duration cannot be negative!");
        }

        this.spamPreventionDuration = spamPreventionDuration;
        return (B) this;
    }

    /**
     * Adds a {@link GuiComponent} to the {@link BaseGui}.
     * <p>
     * The {@link FunctionalGuiComponentBuilder} will create a {@link FunctionalGuiComponent},
     * which by itself is <b>NOT</b> a {@link GuiComponent} but instead a {@link GuiComponentProducer}.<p>
     * This will in turn build a real {@link GuiComponent} before being added to the {@link BaseGui}.
     *
     * @param builder The functional component builder.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B component(final @NotNull FunctionalGuiComponentBuilder<P, I> builder) {
        final var componentRenderer = new SimpleFunctionalGuiComponent<P, I>();
        builder.accept(componentRenderer);
        components.add(componentRenderer.asGuiComponent());
        return (B) this;
    }

    /**
     * Adds a {@link GuiComponent} to the {@link BaseGui}.
     *
     * @param component Any type of {@link GuiComponent}.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B component(final @NotNull GuiComponent<P, I> component) {
        components.add(component);
        return (B) this;
    }

    /**
     * Finalizes the builder and creates a {@link G} instance of {@link BaseGui} depending on the platform.
     *
     * @return {@link G} which is a {@link BaseGui}.
     */
    public abstract G build();

    // ---------------- Internal getters ---------------- //

    protected @NotNull GuiContainerType getContainerType() {
        return containerType;
    }

    protected @NotNull List<GuiComponent<P, I>> getComponents() {
        return components;
    }

    protected @NotNull ClickHandler<P> getClickHandler() {
        if (clickHandler == null) {
            return guiSettings.getClickHandler();
        }

        return clickHandler;
    }

    protected @NotNull GuiComponentRenderer<P, I> getComponentRenderer() {
        if (componentRenderer == null) {
            return guiSettings.getComponentRenderer();
        }

        return componentRenderer;
    }

    protected @NotNull Component getTitle() {
        if (title == null) {
            throw new TriumphGuiException("Cannot create GUI with empty title!");
        }

        return title;
    }

    protected long getSpamPreventionDuration() {
        if (spamPreventionDuration < 0) {
            return guiSettings.getSpamPreventionDuration();
        }

        return spamPreventionDuration;
    }
}
