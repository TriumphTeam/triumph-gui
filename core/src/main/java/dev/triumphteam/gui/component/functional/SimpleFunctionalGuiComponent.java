package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.exception.GuiException;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.builtin.SimpleState;
import dev.triumphteam.gui.state.State;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> implements FunctionalGuiComponent<P, I> {

    private final List<State> states = new ArrayList<>();
    private GuiComponentRender<P, I> component = null;

    @Override
    public @NotNull State state(final @NotNull State state) {
        states.add(state);
        return state;
    }

    @Override
    public @NotNull <T> MutableState<@NotNull T> state(@NotNull final T value) {
        return state(value, StateMutationPolicy.StructuralEquality.INSTANCE);
    }

    @Override
    public @NotNull <T> MutableState<@NotNull T> state(
        final @NotNull T value,
        final @NotNull StateMutationPolicy mutationPolicy
    ) {
        var state = new SimpleState<>(value, mutationPolicy);
        states.add(state);
        return state;
    }

    @Override
    public @NotNull <T> MutableState<@Nullable T> nullableState(final @Nullable T value) {
        return nullableState(value, StateMutationPolicy.StructuralEquality.INSTANCE);
    }

    @Override
    public @NotNull <T> MutableState<@Nullable T> nullableState(
        final @Nullable T value,
        final @NotNull StateMutationPolicy mutationPolicy
    ) {
        var state = new SimpleState<>(value, mutationPolicy);
        states.add(state);
        return state;
    }

    @Override
    public void render(final @NotNull GuiComponentRender<P, I> component) {
        this.component = component;
    }

    public @NotNull ReactiveGuiComponent<P, I> asGuiComponent() {
        if (component == null) {
            throw new GuiException("TODO");
        }

        return new FinalComponent<>(states, component);
    }
}
