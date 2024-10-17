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
package dev.triumphteam.gui.component.renderer;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.component.RenderedComponent;
import dev.triumphteam.gui.container.MapBackedContainer;
import org.jetbrains.annotations.NotNull;

public final class DefaultGuiComponentRenderer<P, I> implements GuiComponentRenderer<P, I> {

    @Override
    public void renderComponent(
        final @NotNull P player,
        final @NotNull GuiComponent<P, I> component,
        final @NotNull AbstractGuiView<P, I> view
    ) {

        final var componentClickHandler = component.clickHandler();
        final var container = new MapBackedContainer<P, I>(
            componentClickHandler == null ? view.getDefaultClickHandler() : componentClickHandler,
            view.getContainerType()
        );

        if (component instanceof ReactiveGuiComponent<P, I> reactiveComponent) {
            reactiveComponent.render(container);
        }

        final var renderedItems = container.complete();
        final var renderedComponent = new RenderedComponent<>(component, renderedItems);

        // Complete rendered back in the view
        view.completeRendered(renderedComponent);
    }
}
