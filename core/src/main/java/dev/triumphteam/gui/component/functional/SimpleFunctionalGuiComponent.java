package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.GuiComponentProducer;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> extends AbstractFunctionalGuiComponent<P> implements FunctionalGuiComponent<P, I>, GuiComponentProducer<P, I> {

    private FunctionalGuiComponentRender<P, I> component = null;

    @Override
    public void render(final @NotNull FunctionalGuiComponentRender<P, I> component) {
        this.component = component;
    }

    @Override
    public @NotNull GuiComponent<P, I> asGuiComponent() {
        if (component == null) {
            throw new TriumphGuiException("TODO");
        }

        return new ReactiveGuiComponent<>() {

            @Override
            public @Nullable ClickHandler<P> clickHandler() {
                return getClickHandler();
            }

            @Override
            public void render(final @NotNull GuiContainer<@NotNull P, @NotNull I> container, @NotNull final P player) {
                component.render(container, player);
            }

            @Override
            public @NotNull List<State> states() {
                return getStates();
            }
        };
    }
}
