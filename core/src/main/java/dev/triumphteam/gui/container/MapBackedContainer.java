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
package dev.triumphteam.gui.container;

import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.SimpleGuiClickAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.element.GuiItem;
import dev.triumphteam.gui.element.RenderedClickElement;
import dev.triumphteam.gui.element.RenderedGuiElement;
import dev.triumphteam.gui.element.RenderedGuiItem;
import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MapBackedContainer<P, I> implements GuiContainer<P, I> {

    private final Map<Integer, RenderedGuiElement<P, I>> backing = new HashMap<>(100);
    private final ClickHandler<P> clickHandler;
    private final GuiContainerType containerType;

    public MapBackedContainer(
            final @NotNull ClickHandler<P> clickHandler,
            final @NotNull GuiContainerType containerType
    ) {
        this.clickHandler = clickHandler;
        this.containerType = containerType;
    }

    @Override
    public @NotNull GuiContainerType containerType() {
        return containerType;
    }

    @Override
    public void setItem(final int row, final int column, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        setItem(containerType.toSlot(row, column), guiItem);
    }

    @Override
    public void setItem(final @NotNull Slot slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        setItem(containerType.toSlot(slot.row(), slot.column()), guiItem);
    }

    @Override
    public void setItem(final int slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        // TODO VALIDATE SLOT HERE TOO
        // Render and add item to backing
        backing.put(slot, new RenderedGuiItem<>(guiItem.render(), clickHandler, guiItem.getClickAction()));
    }

    @Override
    public void setAction(final int slot, final @NotNull GuiClickAction<P> clickAction) {
        backing.put(slot, new RenderedClickElement<>(clickHandler, clickAction));
    }

    @Override
    public void setAction(final int row, final int column, final @NotNull GuiClickAction<P> clickAction) {
        setAction(containerType.toSlot(row, column), clickAction);
    }

    @Override
    public void setAction(final @NotNull Slot slot, final @NotNull GuiClickAction<P> clickAction) {
        setAction(containerType.toSlot(slot.row(), slot.column()), clickAction);
    }

    @Override
    public void setAction(final int slot, final @NotNull SimpleGuiClickAction<P> clickAction) {
        backing.put(slot, new RenderedClickElement<>(clickHandler, clickAction));
    }

    @Override
    public void setAction(final int row, final int column, final @NotNull SimpleGuiClickAction<P> clickAction) {
        setAction(containerType.toSlot(row, column), clickAction);
    }

    @Override
    public void setAction(final @NotNull Slot slot, final @NotNull SimpleGuiClickAction<P> clickAction) {
        setAction(containerType.toSlot(slot.row(), slot.column()), clickAction);
    }

    @Override
    public void fill(final @NotNull GuiLayout layout, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        layout.forEach(position -> setItem(position, guiItem));
    }

    public @NotNull Map<Integer, RenderedGuiElement<P, I>> complete() {
        return Collections.unmodifiableMap(backing);
    }
}
