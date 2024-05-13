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
package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.State;
import dev.triumphteam.gui.state.builtin.EmptyState;
import dev.triumphteam.gui.state.policy.StateMutationPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

interface BaseFunctionalGuiComponent<P> {

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

    /**
     * TODO
     * @param clickHandler
     */
    void withClickHandler(final @Nullable ClickHandler<P> clickHandler);

    void withSimpleClickHandler();

    void withCompletableFutureClickHandler();

    void withCompletableFutureClickHandler(final long timeout, final @NotNull TimeUnit unit);
}
