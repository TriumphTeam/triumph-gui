package dev.triumphteam.gui.container;

import dev.triumphteam.gui.element.GuiElement;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class Container {

    private final Map<Slot, GuiElement> backing = new HashMap<>(100);

    public void set(final @NotNull Slot slot, final @NotNull GuiElement guiElement) {
        backing.put(slot, guiElement);
    }
}
