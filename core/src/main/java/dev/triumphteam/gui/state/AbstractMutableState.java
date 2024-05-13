/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.state;

import dev.triumphteam.gui.state.builtin.SimpleMutableState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract implementation for a {@link MutableState}.
 * The mutability of the value {@link T} is dependent on the given {@link StateMutationPolicy}.
 *
 * @param <T> The type of the value.
 * @see SimpleMutableState
 */
public abstract class AbstractMutableState<T> extends AbstractState implements MutableState<T> {

    private final StateMutationPolicy mutationPolicy;
    private T value;

    public AbstractMutableState(final T value, final @NotNull StateMutationPolicy mutationPolicy) {
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
        if (mutationPolicy.equivalent(this.value, value)) return;

        this.value = value;
        trigger();
    }

    @Override
    public @NotNull StateMutationPolicy stateMutationPolicy() {
        return mutationPolicy;
    }
}
