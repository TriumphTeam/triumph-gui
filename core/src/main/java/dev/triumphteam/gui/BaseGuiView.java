package dev.triumphteam.gui;

import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.container.MapBackedContainer;
import dev.triumphteam.gui.item.ItemClickAction;
import dev.triumphteam.gui.item.RenderedItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseGuiView<P, I> {

    protected final Map<Integer, RenderedItem<I>> temporaryCache = new HashMap<>();
    private final P viewer;
    private final List<FinalComponent<P, I>> components;

    public BaseGuiView(
        final @NotNull P viewer,
        final List<FinalComponent<P, I>> components
    ) {
        this.viewer = viewer;
        this.components = components;
    }

    public @NotNull P getViewer() {
        return viewer;
    }

    public abstract void open();

    public abstract void close();

    protected void setup() {
        components.forEach(renderer -> renderer.states().forEach(state -> state.addListener(this, () -> renderComponent(renderer))));

        components.forEach(this::renderComponent);
    }

    private void renderComponent(final @NotNull FinalComponent<P, I> component) {
        final var container = new MapBackedContainer<I>();
        component.component().render(container, viewer);
        populateInventory(container);
    }

    protected abstract void populateInventory(final @NotNull MapBackedContainer<I> container);

    public @Nullable ItemClickAction getAction(final int slot) {
        final var item = temporaryCache.get(slot);
        if (item == null) return null;
        return item.action();
    }
}
