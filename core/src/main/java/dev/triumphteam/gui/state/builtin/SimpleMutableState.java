package dev.triumphteam.gui.state.builtin;

import dev.triumphteam.gui.state.AbstractMutableState;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * The simplest implementation of {@link MutableState}.
 *
 * @param <T> The type of the value.
 * @see AbstractMutableState For the implementation.
 */
public final class SimpleMutableState<T> extends AbstractMutableState<T> {

    public SimpleMutableState(final T value, final @NotNull StateMutationPolicy mutationPolicy) {
        super(value, mutationPolicy);
    }
}
