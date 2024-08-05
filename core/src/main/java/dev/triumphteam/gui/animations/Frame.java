/*
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
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

package dev.triumphteam.gui.animations;

import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Frame {
    private boolean saved = false;
    private final Map<Integer, GuiItem> defaultItems;
    private final Map<Integer, GuiItem> items;

    /**
     * @param items A map with the slot and the item to display in this frame
     */
    public Frame(Map<Integer, GuiItem> items) {
        this.items = items;
        this.defaultItems = new HashMap<>();
    }

    /**
     * @param gui The gui to display this frame
     * @return The same frame instance
     */
    public Frame apply(@NotNull BaseGui gui) {
        if (!saved) {
            for (Map.Entry<Integer, GuiItem> entry : items.entrySet()) {
                defaultItems.put(entry.getKey(), gui.getGuiItem(entry.getKey()));
            }

            saved = true;
        }

        items.forEach(gui::updateItem);
        return this;
    }

    public void fallback(@NotNull BaseGui gui) {
        defaultItems.forEach(gui::updateItem);
    }

    public Map<Integer, GuiItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "items=" + items +
                '}';
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Integer,GuiItem> items = new HashMap<>();

        public Builder addItem(int slot, GuiItem item) {
            items.put(slot, item);
            return this;
        }

        @Contract(value = " -> new", pure = true)
        public @NotNull Frame build() {
            return new Frame(items);
        }
    }
}
