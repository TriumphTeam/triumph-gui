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
 * A representation of a {@link State} that is mutable.
 * Modifying value of type {@link T} will trigger the component associated with this state to re-render.
 * Setting the value to an equal value as the current, may or may not trigger an update,
 * all depending on the implementation of the used {@link MutableState}.
 * Same for the nullability of the value.
 *
 * @param <T> The type the state accepts.
 * @see AbstractMutableState
 * @see SimpleMutableState
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
