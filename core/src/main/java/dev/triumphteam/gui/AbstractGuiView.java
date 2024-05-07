package dev.triumphteam.gui;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.processor.ClickProcessor;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.component.StatefulGuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.item.RenderedGuiItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractGuiView<P, I> implements GuiView<P, I> {

    private final P viewer;
    private final List<GuiComponent<P, I>> components;
    private final GuiComponentRenderer<P, I> renderer;
    private final ClickHandler<P> defaultClickHandler;
    private final GuiContainerType containerType;

    // Click processor
    private final ClickProcessor<P, I> clickProcessor;
    // Cache of rendered components
    private final Map<GuiComponent<P, I>, RenderedComponent<P, I>> renderedComponents = new ConcurrentHashMap<>();
    // All the gui items that have been rendered and are in the inventory
    private final Map<Integer, RenderedGuiItem<P, I>> allRenderedItems = new ConcurrentHashMap<>();

    public AbstractGuiView(
        final @NotNull P viewer,
        final @NotNull List<@NotNull GuiComponent<P, I>> components,
        final @NotNull GuiContainerType containerType,
        final @NotNull GuiComponentRenderer<P, I> renderer,
        final @NotNull ClickHandler<P> defaultClickHandler,
        final @NotNull ClickProcessor<P, I> clickProcessor
    ) {
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

    protected void setup() {
        components.forEach(component -> {

            if (component instanceof StatefulGuiComponent<P, I>) {
                // Add listener to used states
                ((StatefulGuiComponent<P, I>) component).states().forEach(state -> {
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

    public @NotNull ClickProcessor<P, I> getClickProcessor() {
        return clickProcessor;
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
}
