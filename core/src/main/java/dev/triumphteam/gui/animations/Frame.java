package dev.triumphteam.gui.animations;

import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Frame {
    private final Map<Integer, GuiItem> items;

    /**
     * @param items A map with the slot and the item to display in this frame
     */
    public Frame(Map<Integer, GuiItem> items) {
        this.items = items;
    }

    /**
     * @param gui The gui to display this frame
     * @return The same frame instance
     */
    public Frame apply(@NotNull BaseGui gui) {
        items.forEach(gui::updateItem);
        return this;
    }
}
