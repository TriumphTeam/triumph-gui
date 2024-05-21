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

/**
 * The simplest click handler, all it does is run the action given.
 *
 * @param <P> The player type.
 * @see ClickHandler
 * @see GuiClickAction
 * @see CompletableFutureClickHandler for async handler.
 */
public final class SimpleClickHandler<P> implements ClickHandler<P> {

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

        // Run the action
        ((RunnableGuiClickAction<P>) action).run(player, context);
    }
}
