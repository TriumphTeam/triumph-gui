package dev.triumphteam.gui.state.builtin;

import dev.triumphteam.gui.state.BaseMutableState;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * The simplest implementation of {@link MutableState}.
 *
 * @param <T> The type of the value.
 * @see BaseMutableState For the implementation.
 */
public final class SimpleState<T> extends BaseMutableState<T> {

    public SimpleState(final T value, final @NotNull StateMutationPolicy mutationPolicy) {
        super(value, mutationPolicy);
    }
}
