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
package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.click.controller.ClickController;
import dev.triumphteam.gui.exception.TriumphGuiException;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * A {@link ClickHandler} implementation that runs the click action async using {@link CompletableFuture}.
 *
 * @param <P> The player type.
 */
public final class CompletableFutureClickHandler<P> implements ClickHandler<P> {

    private final long timeout;
    private final TimeUnit unit;

    public CompletableFutureClickHandler() {
        this(6, TimeUnit.SECONDS);
    }

    public CompletableFutureClickHandler(final long timeout, final @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public void handle(
        final @NotNull P player,
        final @NotNull ClickContext context,
        final @NotNull GuiClickAction<P> action,
        final @NotNull ClickController controller
    ) {
        // Only accept runnable actions
        if (!(action instanceof RunnableGuiClickAction<P>)) {
            throw new TriumphGuiException("The click action type '" + action.getClass().getName() + "' is supported by the 'CompletableFutureClickHandler'.");
        }

        // Tell the controller that it'll be complete later as to block all new clicks
        controller.completingLater(true);

        // Run the action async and complete click when finished
        CompletableFuture.runAsync(() -> ((RunnableGuiClickAction<P>) action).run(player, context))
            .orTimeout(timeout, unit)
            .whenComplete((unused, throwable) -> controller.complete(throwable));
    }
}
