/**
 * MIT License
 * <p>
 * Copyright (c) 2024 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
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
import dev.triumphteam.gui.click.handler.CompletableFutureClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.state.PagerState;
import dev.triumphteam.gui.state.ScrollerState;
import dev.triumphteam.nova.holder.AbstractStateHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractFunctionalGuiComponent<P> extends AbstractStateHolder implements BaseFunctionalGuiComponent<P> {

    private ClickHandler<P> clickHandler = null;

    @Override
    public @NotNull <T> PagerState<T> rememberPager(
            final int startPage,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout) {
        return remember(PagerState.of(startPage, elements, layout));
    }

    @Override
    public @NotNull <T> PagerState<T> rememberPager(final @NotNull List<T> elements, final @NotNull GuiLayout layout) {
        return remember(PagerState.of(elements, layout));
    }

    @Override
    public @NotNull <T> ScrollerState<T> rememberScroller(
            final int steps,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    ) {
        return remember(ScrollerState.of(steps, elements, layout));
    }

    @Override
    public void withClickHandler(final @Nullable ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public void withSimpleClickHandler() {
        this.clickHandler = new SimpleClickHandler<>();
    }

    @Override
    public void withCompletableFutureClickHandler() {
        this.clickHandler = new CompletableFutureClickHandler<>();
    }

    @Override
    public void withCompletableFutureClickHandler(final long timeout, final @NotNull TimeUnit unit) {
        this.clickHandler = new CompletableFutureClickHandler<>(timeout, unit);
    }

    public @Nullable ClickHandler<P> getClickHandler() {
        return clickHandler;
    }
}
