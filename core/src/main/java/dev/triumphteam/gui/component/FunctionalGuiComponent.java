package dev.triumphteam.gui.component;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.State;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to a {@link GuiComponent} this component will take in states and render a component.
 * Unlike {@link GuiComponent} it is not meant to be extended upon and is only used by the {@link BaseGuiBuilder}.
 *
 * @param <P> The player type.
 * @param <I> The item type.
 */
public interface FunctionalGuiComponent<P, I> {

    /**
     * Associate a {@link State} to the component.
     *
     * @param state A state.
     * @return The same state passed.
     */
    @NotNull
    State state(final @NotNull State state);

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

    /**
     * A component render function.
     * The function inside works the same as a normal {@link GuiComponent#render(GuiContainer, Object)} would.
     *
     * @param render The component render.
     */
    void render(final @NotNull GuiComponentRender<@NotNull P, @NotNull I> render);
}
