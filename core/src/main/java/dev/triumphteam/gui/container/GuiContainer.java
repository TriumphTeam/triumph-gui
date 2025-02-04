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
package dev.triumphteam.gui.container;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public interface GuiContainer<P, I> {

    @NotNull GuiContainerType containerType();

    void setItem(final int slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);

    void setItem(final int row, final int column, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);

    void setItem(final @NotNull Slot slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);

    void fill(final @NotNull GuiLayout layout, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);
}
