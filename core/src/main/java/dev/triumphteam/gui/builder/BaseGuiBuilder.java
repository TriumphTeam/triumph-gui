/**
 * MIT License
 * <p>
 * Copyright (c) 2024 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
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
import dev.triumphteam.gui.actions.GuiCloseAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.SimpleGuiComponent;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponent;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentRender;
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.settings.GuiSettings;
import dev.triumphteam.gui.title.GuiTitle;
import dev.triumphteam.gui.title.SimpleGuiTitle;
import dev.triumphteam.gui.title.functional.FunctionalGuiTitle;
import dev.triumphteam.gui.title.functional.SimpleFunctionalGuiTitle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
public abstract class BaseGuiBuilder<B extends BaseGuiBuilder<B, P, G, I, C>, P, G extends BaseGui<P>, I, C extends GuiContainerType> {

    private final GuiSettings<P, I, ?> guiSettings;
    private final List<GuiComponent<P, I>> components = new ArrayList<>();
    private final List<GuiCloseAction> closeActions = new ArrayList<>();
    private C containerType;
    private ClickHandler<P> clickHandler = null;
    private GuiComponentRenderer<P, I> componentRenderer = null;
    private GuiTitle title = null;
    private long spamPreventionDuration = -1;

    public BaseGuiBuilder(
        final GuiSettings<P, I, ?> guiSettings,
        final @NotNull C defaultContainerType
    ) {
        this.guiSettings = guiSettings;
        this.containerType = defaultContainerType;
    }

    /**
     * Sets the title of the {@link BaseGui}.
     *
     * @param title The title {@link GuiTitle}.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B title(final @NotNull GuiTitle title) {
        this.title = title;
        return (B) this;
    }

    /**
     * Sets the title of the {@link BaseGui}.
     *
     * @param title The title {@link Component}.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B title(final @NotNull Component title) {
        return title(new SimpleGuiTitle(() -> title, Collections.emptyList()));
    }

    /**
     * Sets the title of the {@link BaseGui}.
     * This version is reactive and will update when the provided state changes.
     *
     * @param title The title builder function.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    public @NotNull B title(final @NotNull Consumer<@NotNull FunctionalGuiTitle> title) {
        final var simpleTitle = new SimpleFunctionalGuiTitle();
        title.accept(simpleTitle);
        return title(simpleTitle.asGuiTitle());
    }

    /**
     * Sets the container type of the GUI.
     *
     * @param containerType The {@link GuiContainerType} to be used.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B containerType(final @NotNull C containerType) {
        this.containerType = containerType;
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
     * The {@link Consumer} will create a {@link FunctionalGuiComponent},
     * which by itself is <b>NOT</b> a {@link GuiComponent}.<p>
     * This will in turn build a real {@link GuiComponent} before being added to the {@link BaseGui}.
     *
     * @param component The functional component builder.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B component(final @NotNull Consumer<@NotNull FunctionalGuiComponent<P, I>> component) {
        final var simpleComponent = new SimpleFunctionalGuiComponent<P, I>();
        component.accept(simpleComponent);
        components.add(simpleComponent.asGuiComponent());
        return (B) this;
    }

    /**
     * For faster creation of components that don't require states.
     * Use this for components you don't need to update, aka static items.
     *
     * @param render The component render.
     * @return The {@link B} instance of the {@link BaseGuiBuilder}.
     */
    @Contract("_ -> this")
    public @NotNull B statelessComponent(final @NotNull FunctionalGuiComponentRender<P, I> render) {
        return component(new SimpleGuiComponent<>(render, Collections.emptyList()));
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

    @Contract("_ -> this")
    public @NotNull B onClose(final @NotNull GuiCloseAction closeAction) {
        closeActions.add(closeAction);
        return (B) this;
    }

    /**
     * Finalizes the builder and creates a {@link G} instance of {@link BaseGui} depending on the platform.
     *
     * @return {@link G} which is a {@link BaseGui}.
     */
    public abstract G build();

    // ---------------- Internal getters ---------------- //

    protected @NotNull C getContainerType() {
        return containerType;
    }

    protected @NotNull List<GuiComponent<P, I>> getComponents() {
        return components;
    }

    protected @NotNull List<GuiCloseAction> getCloseActions() {
        return closeActions;
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

    protected @NotNull GuiTitle getTitle() {
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
