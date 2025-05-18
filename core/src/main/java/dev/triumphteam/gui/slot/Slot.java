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
package dev.triumphteam.gui.slot;

import org.jetbrains.annotations.NotNull;

import static dev.triumphteam.gui.container.type.types.AbstractAnvilContainerType.ANVIL_ROW;
import static dev.triumphteam.gui.container.type.types.AbstractHopperContainerType.HOPPER_ROW;

/**
 * Represents a position with a row and column in a grid-like structure.
 * This record is immutable and provides utility methods for defining specific slots.
 * Slots start at 1 for both rows and columns.
 */
public record Slot(int row, int column) {

    /**
     * Creates a new {@code Slot} instance based on the specified row and column.
     *
     * @param row    The row of the slot. Rows are 1-based, with the first row starting at 1.
     * @param column The column of the slot. Columns are also 1-based, with the first column starting at 1.
     * @return A new {@code Slot} instance corresponding to the given row and column.
     */
    public static Slot of(final int row, final int column) {
        return new Slot(row, column);
    }

    /**
     * Creates a {@code Slot} instance representing the origin of a grid-like structure.
     * The origin is defined as the first row and the first column of the grid (1, 1).
     *
     * @return A {@code Slot} instance located at the origin of the grid, where both the row and column are 1.
     */
    public static Slot origin() {return new Slot(1, 1);}

    /**
     * Creates a {@code Slot} instance representing the leftmost column of the specified row.
     *
     * @param rows The row for which the minimum slot (first column) is to be created.
     *             Row numbers are 1-based, with the first row starting at 1.
     * @return A {@code Slot} instance corresponding to the specified row and the first column.
     */
    public static Slot min(final int rows) {return new Slot(rows, 1);}

    /**
     * Creates a {@code Slot} instance representing the rightmost column of the specified row.
     * The "max" slot in a row is defined as the slot in the 9th column.
     *
     * @param rows The row for which the maximum slot (last column) is to be created.
     *             Row numbers are 1-based, with the first row starting at 1.
     * @return A {@code Slot} instance corresponding to the specified row and the 9th column.
     */
    public static Slot max(final int rows) {return new Slot(rows, 9);}

    /**
     * Utility class for creating {@link Slot} instances specific to hoppers.
     * Hoppers are designed with a single row layout, and this class provides
     * a method to map a column index within that row to a corresponding {@link Slot}.
     */
    public static final class Hopper {

        private Hopper() {
            throw new AssertionError("Util class, should not be instantiated");
        }

        /**
         * Creates a {@link Slot} instance for a column in the predefined hopper row.
         * Hoppers are structured as a single-row layout, and this method maps the specified
         * column index within that row to a corresponding {@link Slot}.
         *
         * @param slot The column index within the hopper row. Columns are 1-based, with the first column starting at 1.
         * @return A {@link Slot} instance corresponding to the predefined hopper row and the specified column.
         */
        public static @NotNull Slot of(final int slot) {
            return new Slot(HOPPER_ROW, slot);
        }
    }

    /**
     * Utility class representing predefined slots in an anvil inventory.
     * Provides methods to access specific slot positions such as the primary input,
     * secondary input, and result slots, as well as the ability to create slots
     * dynamically based on a given column index.
     */
    public static final class Anvil {

        private Anvil() {
            throw new AssertionError("Util class, should not be instantiated");
        }

        /**
         * Retrieves the slot representing the primary input of an anvil inventory.
         *
         * @return A {@link Slot} instance representing the primary input slot in the anvil.
         * The slot resides in the predefined anvil row and has a column index of 0.
         */
        public static @NotNull Slot primaryInput() {
            return new Slot(ANVIL_ROW, 0);
        }

        /**
         * Retrieves the slot representing the secondary input of an anvil inventory.
         *
         * @return A {@link Slot} instance representing the secondary input slot in the anvil.
         * The slot resides in the predefined anvil row and has a column index of 1.
         */
        public static @NotNull Slot secondaryInput() {
            return new Slot(ANVIL_ROW, 1);
        }

        /**
         * Retrieves the slot representing the result of an anvil inventory.
         *
         * @return A {@link Slot} instance representing the result slot in the anvil.
         * The slot resides in the predefined anvil row and has a column index of 2.
         */
        public static @NotNull Slot result() {
            return new Slot(ANVIL_ROW, 2);
        }

        /**
         * Creates a {@code Slot} instance for a given column index in the predefined anvil row.
         *
         * @param slot The column index within the anvil row. Columns are 1-based, with the first column starting at 1.
         * @return A {@code Slot} instance corresponding to the predefined anvil row and the specified column index.
         * The row is predefined as the anvil row.
         */
        public static @NotNull Slot of(final int slot) {
            return new Slot(ANVIL_ROW, slot);
        }
    }
}
