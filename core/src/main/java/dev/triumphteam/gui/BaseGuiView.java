package dev.triumphteam.gui;

import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.item.ItemClickAction;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseGuiView<P, I> implements GuiView<P, I> {

    private final P viewer;
    private final List<GuiComponent<P, I>> components;
    private final GuiComponentRenderer<P, I> renderer;

    private final Map<GuiComponent<P, I>, RenderedComponent<P, I>> renderedComponents = new ConcurrentHashMap<>();
    private final Map<Slot, ItemClickAction> componentClickActions = new ConcurrentHashMap<>();

    public BaseGuiView(
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
            // Add listener to used states
            component.states().forEach(state -> {
                state.addListener(this, () -> renderer.renderComponent(viewer, component, this));
            });

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

    protected abstract void populateInventory(final @NotNull Map<Slot, RenderedGuiItem<I>> renderedItems);

    public @Nullable ItemClickAction getAction(final int slot) {
        return componentClickActions.get(new Slot(slot));
    }
}
