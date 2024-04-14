package dev.triumphteam.gui;

import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.container.Container;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseGuiView<P, V extends BaseGuiView<P, V>> {

    private final P viewer;
    private final V parent;
    private final List<FinalComponent<P, V>> components;

    public BaseGuiView(
        final @NotNull P viewer,
        final V parent,
        final List<FinalComponent<P, V>> components
    ) {
        this.viewer = viewer;
        this.parent = parent;
        this.components = components;
    }

    public @NotNull P getViewer() {
        return viewer;
    }

    public @Nullable V getParent() {
        return parent;
    }

    public abstract void open();

    public abstract void close();

    protected void setup() {
        components.forEach(renderer -> {
            renderer.states().forEach(state -> {
                state.addListener(this, () -> {
                    renderComponent(renderer);
                });
            });
        });
    }

    private void renderComponent(final @NotNull FinalComponent<P, V> component) {
        final var container = new Container();
        //noinspection unchecked
        component.component().render(container, viewer, (V) this);
    }

    protected abstract void populateInventory(final @NotNull Container container);
}
