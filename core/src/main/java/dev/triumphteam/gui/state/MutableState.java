package dev.triumphteam.gui.state;

import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * A representation of a {@link State} that is mutable.
 * Modifying value of type {@link T} will trigger the component associated with this state to re-render.
 * Setting the value to an equal value as the current, may or may not trigger an update,
 * all depending on the implementation of the used {@link MutableState}.
 * Same for the nullability of the value.
 *
 * @param <T> The type the state accepts.
 * @see BaseMutableState
 * @see SimpleState
 */
public interface MutableState<T> extends State {

    /**
     * Gets the current value of the state.
     * The nullability of this value depends on the value passed.
     *
     * @return The value of the state.
     */
    T getValue();

    /**
     * Set a new value to the state.
     * This may or may not trigger a component to re-draw.
     *
     * @param value The new value of the state.
     */
    void setValue(final T value);

    /**
     * Which mutation policy is used by this state.
     * The mutation policy is not used outside the state itself,
     * so it can be ignored if not actively used but custom implementations of {@link MutableState}.
     *
     * @return The used mutation policy.
     */
    @NotNull
    StateMutationPolicy stateMutationPolicy();
}
