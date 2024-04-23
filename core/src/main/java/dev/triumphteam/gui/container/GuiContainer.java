package dev.triumphteam.gui.container;

import dev.triumphteam.gui.element.GuiElement;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public interface GuiContainer<I> {

    void set(final int slot, final @NotNull GuiElement<@NotNull I> guiElement);

    void set(final int row, final int column, final @NotNull GuiElement<@NotNull I> guiElement);

    void set(final @NotNull Slot slot, final @NotNull GuiElement<@NotNull I> guiElement);
}
