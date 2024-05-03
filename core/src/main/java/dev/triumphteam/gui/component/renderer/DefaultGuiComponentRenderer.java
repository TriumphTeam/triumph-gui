package dev.triumphteam.gui.component.renderer;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.container.MapBackedContainer;
import org.jetbrains.annotations.NotNull;

public final class DefaultGuiComponentRenderer<P, I> implements GuiComponentRenderer<P, I> {

    @Override
    public void renderComponent(
        final @NotNull P player,
        final @NotNull GuiComponent<P, I> component,
        final @NotNull AbstractGuiView<P, I> view
    ) {

        final var container = new MapBackedContainer<I>();

        if (component instanceof ReactiveGuiComponent) {
            ((ReactiveGuiComponent<P, I>) component).render(container, player);
        }

        final var renderedItems = container.complete();
        final var renderedComponent = new RenderedComponent<>(component, renderedItems);

        // Complete rendered back in the view
        view.completeRendered(renderedComponent);
    }
}
