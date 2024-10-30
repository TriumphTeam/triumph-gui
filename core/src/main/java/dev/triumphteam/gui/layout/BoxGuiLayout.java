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
package dev.triumphteam.gui.layout;

import dev.triumphteam.gui.slot.Slot;
import org.checkerframework.checker.units.qual.min;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BoxGuiLayout implements GuiLayout {

    private final List<Slot> slots = new ArrayList<>();

    public BoxGuiLayout(final @NotNull Slot min, final @NotNull Slot max) {
        this(min, max, Direction.VERTICAL);
    }

    public BoxGuiLayout(final @NotNull Slot min, final @NotNull Slot max, final @NotNull Direction direction) {
        switch (direction) {
            case HORIZONTAL -> {
                for (int col = min.column(); col <= max.column(); col++) {
                    for (int row = min.row(); row <= max.row(); row++) {
                        slots.add(Slot.of(row, col));
                    }
                }
            }
            case VERTICAL -> {
                for (int row = min.row(); row <= max.row(); row++) {
                    for (int col = min.column(); col <= max.column(); col++) {
                        slots.add(Slot.of(row, col));
                    }
                }
            }
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

    public enum Direction {
        HORIZONTAL, VERTICAL
    }
}
