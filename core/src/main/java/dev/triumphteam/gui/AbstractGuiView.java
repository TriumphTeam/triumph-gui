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
package dev.triumphteam.gui;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.processor.ClickProcessor;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.component.StatefulGuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.title.GuiTitle;
import dev.triumphteam.gui.title.StatefulGuiTitle;
import dev.triumphteam.gui.title.renderer.DefaultGuiTitleRenderer;
import dev.triumphteam.gui.title.renderer.GuiTitleRenderer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractGuiView<P, I> implements GuiView<P, I> {

    private final P viewer;
    private final GuiTitle title;
    private final List<GuiComponent<P, I>> components;
    private final GuiComponentRenderer<P, I> renderer;
    private final ClickHandler<P> defaultClickHandler;
    private final GuiContainerType containerType;
    private final GuiTitleRenderer titleRenderer = new DefaultGuiTitleRenderer();

    private final AtomicBoolean firstOpen = new AtomicBoolean(true);
    // Click processor
    private final ClickProcessor<P, I> clickProcessor;
    // Cache of rendered components
    private final Map<GuiComponent<P, I>, RenderedComponent<P, I>> renderedComponents = new ConcurrentHashMap<>();
    // All the gui items that have been rendered and are in the inventory
    private final Map<Integer, RenderedGuiItem<P, I>> allRenderedItems = new ConcurrentHashMap<>();
    private Component renderedTitle = null;

    public AbstractGuiView(
        final @NotNull P viewer,
        final @NotNull GuiTitle title,
        final @NotNull List<@NotNull GuiComponent<P, I>> components,
        final @NotNull GuiContainerType containerType,
        final @NotNull GuiComponentRenderer<P, I> renderer,
        final @NotNull ClickHandler<P> defaultClickHandler,
        final @NotNull ClickProcessor<P, I> clickProcessor
    ) {
        this.title = title;
        this.viewer = viewer;
        this.components = components;
        this.containerType = containerType;
        this.renderer = renderer;
        this.defaultClickHandler = defaultClickHandler;
        this.clickProcessor = clickProcessor;
    }

    public @NotNull P viewer() {
        return viewer;
    }

    public abstract @NotNull String viewerName();

    public abstract @NotNull UUID viewerUuid();

    protected abstract void clearSlot(final int slot);

    protected abstract void populateInventory(final @NotNull Map<Integer, @NotNull RenderedGuiItem<P, I>> renderedItems);

    protected abstract void openInventory();

    @Override
    public void open() {
        if (title instanceof StatefulGuiTitle statefulGuiTitle) {
            // Add listener to used states
            statefulGuiTitle.states().forEach(state -> {
                state.addListener(this, () -> {
                    firstOpen.compareAndSet(true, false);
                    titleRenderer.renderTitle(title, (rendered) -> {
                        this.renderedTitle = rendered;
                        openInventory();
                    });
                });
            });
        }

        titleRenderer.renderTitle(title, (rendered) -> {
            this.renderedTitle = rendered;
            openInventory();
            setup();
        });
    }

    protected void setup() {
        components.forEach(component -> {

            if (component instanceof StatefulGuiComponent<P, I> statefulComponent) {
                // Add listener to used states
                statefulComponent.states().forEach(state -> {
                    state.addListener(this, () -> renderer.renderComponent(viewer, component, this));
                });
            }

            // Then render component
            renderer.renderComponent(viewer, component, this);
        });
    }

    public void completeRendered(final @NotNull RenderedComponent<P, I> renderedComponent) {
        var ownerComponent = renderedComponent.component();
        // Check if component was already rendered before
        var existing = renderedComponents.get(ownerComponent);
        if (existing != null) {
            // Clear its uses
            existing.renderedItems().forEach((slot, ignored) -> {
                clearSlot(slot);
                allRenderedItems.remove(slot);
            });
        }

        renderedComponents.put(ownerComponent, renderedComponent);

        final var renderedItems = renderedComponent.renderedItems();
        allRenderedItems.putAll(renderedItems);

        populateInventory(renderedItems);
    }

    public void processClick(final @NotNull ClickContext context) {
        clickProcessor.processClick(context, this);
    }

    public @Nullable RenderedGuiItem<P, I> getItem(final int slot) {
        return allRenderedItems.get(slot);
    }

    public @NotNull ClickHandler<P> getDefaultClickHandler() {
        return defaultClickHandler;
    }

    public @NotNull GuiContainerType getContainerType() {
        return containerType;
    }

    public Component getTitle() {
        if (renderedTitle == null) {
            throw new TriumphGuiException("Tried to get title before it was available.");
        }
        return renderedTitle;
    }

    public boolean isFirstOpen() {
        return firstOpen.get();
    }
}