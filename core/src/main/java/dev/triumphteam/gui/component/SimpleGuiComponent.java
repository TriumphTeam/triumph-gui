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
package dev.triumphteam.gui.component;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentRender;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.nova.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SimpleGuiComponent<P, I> implements ReactiveGuiComponent<P, I> {

    private final FunctionalGuiComponentRender<P, I> component;
    private final List<State> states;
    private final ClickHandler<P> clickHandler;

    public SimpleGuiComponent(
        final @NotNull FunctionalGuiComponentRender<P, I> component,
        final @NotNull List<State> states,
        final @Nullable ClickHandler<P> clickHandler
    ) {
        this.component = component;
        this.states = states;
        this.clickHandler = clickHandler;
    }

    @Override
    public void render(final @NotNull GuiContainer<@NotNull P, @NotNull I> container) {
        component.render(container);
    }

    @Override
    public @NotNull List<State> states() {
        return states;
    }

    @Override
    public @Nullable ClickHandler<P> clickHandler() {
        return clickHandler;
    }
}
