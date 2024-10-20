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
package dev.triumphteam.gui.title.renderer;

import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.title.GuiTitle;
import dev.triumphteam.gui.title.ReactiveGuiTitle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class DefaultGuiTitleRenderer implements GuiTitleRenderer {

    @Override
    public void renderTitle(
        final @NotNull GuiTitle title,
        final @NotNull Consumer<Component> thenRun
    ) {

        if (!(title instanceof ReactiveGuiTitle reactiveGuiTitle)) {
            throw new TriumphGuiException("Could not render title as it is not supported by the current renderer.");
        }

        final var component = reactiveGuiTitle.render();
        thenRun.accept(component);
    }
}
