package dev.triumphteam.gui.state;

import dev.triumphteam.gui.GuiView;
import dev.triumphteam.gui.state.builtin.SimpleState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * Base implementation for a {@link MutableState}.
 * The mutability of the value {@link T} is dependent on the given {@link StateMutationPolicy}.
 *
 * @param <T> The type of the value.
 * @see SimpleState
 */
public abstract class BaseMutableState<T> implements MutableState<T> {

    private final StateListenerContainer listenerContainer = new StateListenerContainer();

    private final StateMutationPolicy mutationPolicy;
    private T value;

    public BaseMutableState(final T value, final @NotNull StateMutationPolicy mutationPolicy) {
        this.value = value;
        this.mutationPolicy = mutationPolicy;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(final T value) {
        // Will not mutate value if they are equivalent
        if (!mutationPolicy.equivalent(this.value, value)) return;

        this.value = value;
        trigger();
    }

    @Override
    public @NotNull StateMutationPolicy stateMutationPolicy() {
        return mutationPolicy;
    }

    @Override
    public void trigger() {
        listenerContainer.triggerAll();
    }

    @Override
    public void addListener(final @NotNull GuiView<?, ?> view, final @NotNull Runnable listener) {
        listenerContainer.addListener(view, listener);
    }
}
