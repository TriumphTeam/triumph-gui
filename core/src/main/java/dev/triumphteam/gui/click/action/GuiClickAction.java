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
package dev.triumphteam.gui.click.action;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.handler.CompletableFutureClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an action for when a player clicks on the GUI.
 * This has no run method because it can be different on each implementation.
 * The implementation of the click action is entirely dependent on the
 * {@link ClickHandler} used.
 *
 * @param <P> The player type.
 * @see SimpleGuiClickAction
 * @see ClickHandler
 * @see SimpleClickHandler
 * @see CompletableFutureClickHandler
 */
public interface GuiClickAction<P> {

    /**
     * Wraps the provided {@link SimpleGuiClickAction} and returns it as a {@link GuiClickAction}.
     *
     * @param <P>    The type of the player interacting with the GUI.
     * @param action The {@link SimpleGuiClickAction} to be wrapped and returned as a {@link GuiClickAction}.
     * @return The provided {@link SimpleGuiClickAction} as a {@link GuiClickAction}.
     */
    static <P> @NotNull GuiClickAction<P> simple(final @NotNull SimpleGuiClickAction<P> action) {
        return action;
    }

    /**
     * Wraps the provided {@link MovableGuiClickAction} and returns it as a {@link GuiClickAction}.
     *
     * @param <P>    The type of the player interacting with the GUI.
     * @param action The {@link MovableGuiClickAction} to be wrapped and returned as a {@link GuiClickAction}.
     * @return The provided {@link MovableGuiClickAction} as a {@link GuiClickAction}.
     */
    static <P> @NotNull GuiClickAction<P> movable(final @NotNull MovableGuiClickAction<P> action) {
        return action;
    }
}
