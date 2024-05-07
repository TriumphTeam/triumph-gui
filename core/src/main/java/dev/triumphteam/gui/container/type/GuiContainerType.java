package dev.triumphteam.gui.container.type;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public interface GuiContainerType {

    int mapSlot(final @NotNull Slot slot);

    Slot mapSlot(final int slot);
}
