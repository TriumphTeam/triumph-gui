package dev.triumphteam.gui.container;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public interface GuiContainer<P, I> {

    @NotNull
    GuiContainerType containerType();

    void set(final int slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);

    void set(final int row, final int column, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);

    void set(final @NotNull Slot slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem);
}
