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
import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.state.pagination.PagerState;
import dev.triumphteam.gui.state.pagination.ScrollerState;
import dev.triumphteam.nova.holder.StateHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

interface BaseFunctionalGuiComponent<P> extends StateHolder {

    <T> @NotNull PagerState<T> rememberPager(
            final int startPage,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    );

    <T> @NotNull PagerState<T> rememberPager(
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    );

    <T> @NotNull ScrollerState<T> rememberScroller(
            final int steps,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
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
