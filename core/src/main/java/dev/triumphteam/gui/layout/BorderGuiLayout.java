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
package dev.triumphteam.gui.layout;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BorderGuiLayout implements GuiLayout {

    private final List<Slot> slots = new ArrayList<>();

    public BorderGuiLayout(final int rows) {
        this(Slot.origin(), Slot.max(rows));
    }

    public BorderGuiLayout(final @NotNull Slot min, final @NotNull Slot max) {
        // Top border
        for (int col = min.column(); col <= max.column(); col++) {
            slots.add(Slot.of(min.row(), col));
        }

        // Bottom border
        for (int col = min.column(); col <= max.column(); col++) {
            slots.add(Slot.of(max.row(), col));
        }

        // Left border
        for (int row = min.row() + 1; row < max.row(); row++) {
            slots.add(Slot.of(row, min.column()));
        }

        // Right border
        for (int row = min.row() + 1; row < max.row(); row++) {
            slots.add(Slot.of(row, max.column()));
        }
    }

    @Override
    public @NotNull Iterator<Slot> iterator() {
        return slots.iterator();
    }

    @Override
    public int size() {
        return slots.size();
    }
}
