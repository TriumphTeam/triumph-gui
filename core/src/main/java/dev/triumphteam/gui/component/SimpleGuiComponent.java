package dev.triumphteam.gui.component;

import dev.triumphteam.gui.component.functional.FunctionalGuiComponentRender;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.nova.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SimpleGuiComponent<P, I> implements ReactiveGuiComponent<P, I> {

    private final FunctionalGuiComponentRender<P, I> component;
    private final List<State> states;

    public SimpleGuiComponent(final @NotNull FunctionalGuiComponentRender<P, I> component, final @NotNull List<State> states) {
        this.component = component;
        this.states = states;
    }

    @Override
    public void render(final @NotNull GuiContainer<@NotNull P, @NotNull I> container) {
        component.render(container);
    }

    @Override
    public @NotNull List<State> states() {
        return states;
    }
}
