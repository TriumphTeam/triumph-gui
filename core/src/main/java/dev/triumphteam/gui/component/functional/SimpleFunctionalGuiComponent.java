package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.exception.GuiException;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> extends AbstractFunctionalStateContainer implements FunctionalGuiComponent<P, I>, GuiComponentProducer<P, I> {

    private GuiComponentRender<P, I> component = null;

    @Override
    public void render(final @NotNull GuiComponentRender<P, I> component) {
        this.component = component;
    }

    @Override
    public @NotNull GuiComponent<P, I> asGuiComponent() {
        if (component == null) {
            throw new GuiException("TODO");
        }

        return new ReactiveGuiComponent<>() {
            @Override
            public void render(final @NotNull GuiContainer<@NotNull I> container, @NotNull final P player) {
                component.render(container, player);
            }

            @Override
            public @NotNull List<State> states() {
                return getStates();
            }
        };
    }
}
