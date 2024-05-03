package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.State;
import dev.triumphteam.gui.state.builtin.EmptyState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface FunctionalStateContainer {

    /**
     * Associate am empty {@link State} to the component.
     *
     * @return A new {@link EmptyState}.
     */
    @NotNull
    State state();

    /**
     * Associate a {@link S} type {@link State} to the component.
     *
     * @param state A state.
     * @return The same state passed.
     */
    @NotNull
    <S extends State> S state(final @NotNull S state);

    /**
     * Create a new state with the given default value.
     *
     * @param value The default value of the state.
     * @param <T>   The type of the value.
     * @return The newly created {@link MutableState}.
     */
    <T> @NotNull MutableState<@NotNull T> state(final @NotNull T value);

    /**
     * Create a new state with the given default value.
     * Uses the given {@link StateMutationPolicy} for equivalence check.
     *
     * @param value          The default value of the state.
     * @param mutationPolicy The mutation policy to use.
     * @param <T>            The type of the value.
     * @return The newly created {@link MutableState}.
     */
    <T> @NotNull MutableState<@NotNull T> state(
        final @NotNull T value,
        final @NotNull StateMutationPolicy mutationPolicy
    );

    /**
     * Create a new state with the given default value.
     * In this case, the value is nullable.
     *
     * @param value The default value of the state.
     * @param <T>   The type of the value.
     * @return The newly created {@link MutableState}.
     */
    <T> @NotNull MutableState<@Nullable T> nullableState(final @Nullable T value);

    /**
     * Create a new state with the given default value.
     * In this case, the value is nullable.
     * Uses the given {@link StateMutationPolicy} for equivalence check.
     *
     * @param value          The default value of the state.
     * @param mutationPolicy The mutation policy to use.
     * @param <T>            The type of the value.
     * @return The newly created {@link MutableState}.
     */
    <T> @NotNull MutableState<@Nullable T> nullableState(
        final @Nullable T value,
        final @NotNull StateMutationPolicy mutationPolicy
    );
}
