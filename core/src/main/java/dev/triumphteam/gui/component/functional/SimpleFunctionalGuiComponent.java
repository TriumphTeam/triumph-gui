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
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.GuiComponentProducer;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.nova.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> extends AbstractFunctionalGuiComponent<P> implements FunctionalGuiComponent<P, I>, GuiComponentProducer<P, I> {

    private FunctionalGuiComponentRender<P, I> component = null;

    @Override
    public void render(final @NotNull FunctionalGuiComponentRender<P, I> component) {
        this.component = component;
    }

    @Override
    public @NotNull GuiComponent<P, I> asGuiComponent() {
        if (component == null) {
            throw new TriumphGuiException("TODO");
        }

        return new ReactiveGuiComponent<>() {

            @Override
            public @Nullable ClickHandler<P> clickHandler() {
                return getClickHandler();
            }

            @Override
            public void render(final @NotNull GuiContainer<@NotNull P, @NotNull I> container, @NotNull final P player) {
                component.render(container, player);
            }

            @Override
            public @NotNull List<State> states() {
                return getStates();
            }
        };
    }
}
