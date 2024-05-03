package dev.triumphteam.gui;

import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.component.StatefulGuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractGuiView<P, I> implements GuiView<P, I> {

    private final P viewer;
    private final List<GuiComponent<P, I>> components;
    private final GuiComponentRenderer<P, I> renderer;

    // Cache of rendered components
    private final Map<GuiComponent<P, I>, RenderedComponent<P, I>> renderedComponents = new ConcurrentHashMap<>();
    // Cache of click actions for the items in the inventory
    private final Map<Slot, GuiClickAction<P>> componentClickActions = new ConcurrentHashMap<>();

    public AbstractGuiView(
        final @NotNull P viewer,
        final @NotNull List<@NotNull GuiComponent<P, I>> components,
        final @NotNull GuiComponentRenderer<P, I> renderer
    ) {
        this.viewer = viewer;
        this.components = components;
        this.renderer = renderer;
    }

    public @NotNull P getViewer() {
        return viewer;
    }

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
                componentClickActions.remove(slot);
            });
        }

        renderedComponents.put(ownerComponent, renderedComponent);

        final var renderedItems = renderedComponent.renderedItems();
        renderedItems.forEach((slot, renderedItem) -> {
            componentClickActions.put(slot, renderedItem.action());
        });

        populateInventory(renderedItems);
    }

    protected abstract void clearSlot(final @NotNull Slot slot);

    protected abstract void populateInventory(final @NotNull Map<Slot, RenderedGuiItem<P, I>> renderedItems);

    public @Nullable GuiClickAction<P> getAction(final int slot) {
        return componentClickActions.get(new Slot(slot));
    }
}
