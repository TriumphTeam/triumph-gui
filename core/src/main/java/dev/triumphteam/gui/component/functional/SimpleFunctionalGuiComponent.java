package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.exception.GuiException;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> extends AbstractFunctionalStateContainer implements FunctionalGuiComponent<P, I>, GuiComponentProducer<P, I> {

    private GuiComponentRender<P, I> component = null;
    private ClickHandler<P> clickHandler;

    @Override
    public void clickHandler(final @Nullable ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
    }

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
            public @Nullable ClickHandler<P> clickHandler() {
                return clickHandler;
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
