package dev.triumphteam.gui.component;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.exception.GuiException;
import dev.triumphteam.gui.state.SimpleState;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class SimpleGuiComponentRenderer<P, V extends BaseGuiView<P, V>> implements GuiComponentRenderer<P, V> {

    private final List<State<?>> states = new ArrayList<>();
    private GuiComponent<P, V> component = null;

    @Override
    public State<Integer> state(final int value) {
        final var state = new SimpleState<>(value);
        states.add(state);
        return state;
    }

    @Override
    public <T> State<T> state(final @NotNull State<T> state) {
        states.add(state);
        return state;
    }

    @Override
    public void render(final @NotNull GuiComponent<P, V> component) {
        this.component = component;
    }

    public @NotNull FinalComponent<P, V> createComponent() {
        if (component == null) {
            throw new GuiException("TODO");
        }

        return new FinalComponent<>(states, component);
    }
}
