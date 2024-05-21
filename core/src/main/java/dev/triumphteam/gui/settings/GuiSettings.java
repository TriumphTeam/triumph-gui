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
package dev.triumphteam.gui.settings;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import dev.triumphteam.gui.component.renderer.DefaultGuiComponentRenderer;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public abstract class GuiSettings<P, I, S extends GuiSettings<P, I, S>> {

    private ClickHandler<P> clickHandler = new SimpleClickHandler<>();
    private GuiComponentRenderer<P, I> componentRenderer = new DefaultGuiComponentRenderer<>();
    private long spamPreventionDuration = 200L;

    public S clickHandler(final @NotNull ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
        return (S) this;
    }

    public S componentRenderer(final @NotNull GuiComponentRenderer<P, I> componentRenderer) {
        this.componentRenderer = componentRenderer;
        return (S) this;
    }

    public S spamPreventionDuration(final long spamPreventionDuration) {
        if (spamPreventionDuration < 0L) {
            throw new IllegalArgumentException("Spam prevention duration cannot be negative!");
        }

        this.spamPreventionDuration = spamPreventionDuration;
        return (S) this;
    }

    public @NotNull ClickHandler<P> getClickHandler() {
        return clickHandler;
    }

    public @NotNull GuiComponentRenderer<P, I> getComponentRenderer() {
        return componentRenderer;
    }

    public long getSpamPreventionDuration() {
        return spamPreventionDuration;
    }
}
