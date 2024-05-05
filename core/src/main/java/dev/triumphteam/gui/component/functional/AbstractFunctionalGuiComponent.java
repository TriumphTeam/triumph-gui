package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.handler.CompletableFutureClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.State;
import dev.triumphteam.gui.state.builtin.EmptyState;
import dev.triumphteam.gui.state.builtin.SimpleMutableState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractFunctionalGuiComponent<P> implements BaseFunctionalGuiComponent<P> {

    private final List<State> states = new ArrayList<>();
    private ClickHandler<P> clickHandler = null;

    @Override
    public @NotNull State state() {
        return state(new EmptyState());
    }

    @Override
    public <S extends State> @NotNull S state(final @NotNull S state) {
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
        var state = new SimpleMutableState<>(value, mutationPolicy);
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
        var state = new SimpleMutableState<>(value, mutationPolicy);
        states.add(state);
        return state;
    }

    @Override
    public void withClickHandler(final @Nullable ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public void withSimpleClickHandler() {
        this.clickHandler = new SimpleClickHandler<>();
    }

    @Override
    public void withCompletableFutureClickHandler() {
        this.clickHandler = new CompletableFutureClickHandler<>();
    }

    @Override
    public void withCompletableFutureClickHandler(final long timeout, final @NotNull TimeUnit unit) {
        this.clickHandler = new CompletableFutureClickHandler<>(timeout, unit);
    }

    public @Nullable ClickHandler<P> getClickHandler() {
        return clickHandler;
    }

    protected @NotNull List<State> getStates() {
        return states;
    }
}
