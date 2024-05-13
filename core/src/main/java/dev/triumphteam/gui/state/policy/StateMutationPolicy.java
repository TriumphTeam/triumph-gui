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
package dev.triumphteam.gui.state.policy;

import dev.triumphteam.gui.state.MutableState;
import org.jetbrains.annotations.Nullable;

/**
 * States how a {@link MutableState} handles equality.
 * By default, {@link StructuralEquality} is used.
 */
public interface StateMutationPolicy {

    /**
     * Checks if the two values are equivalent.
     *
     * @param a The first value.
     * @param b The second value.
     * @return Whether or not they are equivalent.
     */
    boolean equivalent(final @Nullable Object a, final @Nullable Object b);

    /**
     * A {@link StateMutationPolicy} that checks for reference equality.
     * This class is a singleton and {@link ReferenceEquality#INSTANCE} should always be the used instance.
     */
    final class ReferenceEquality implements StateMutationPolicy {

        public static final StateMutationPolicy INSTANCE = new ReferenceEquality();

        @Override
        public boolean equivalent(final @Nullable Object a, final @Nullable Object b) {
            return a == b;
        }
    }

    /**
     * A {@link StateMutationPolicy} that checks for structural equality.
     * This class is a singleton and {@link StructuralEquality#INSTANCE} should always be the used instance.
     */
    final class StructuralEquality implements StateMutationPolicy {

        public static final StateMutationPolicy INSTANCE = new StructuralEquality();

        @Override
        public boolean equivalent(final @Nullable Object a, final @Nullable Object b) {
            if (a == null || b == null) return false;
            return a.equals(b);
        }
    }

    /**
     * A {@link StateMutationPolicy} that makes values never be equivalent.
     * This class is a singleton and {@link NeverEqual#INSTANCE} should always be the used instance.
     */
    final class NeverEqual implements StateMutationPolicy {

        public static final StateMutationPolicy INSTANCE = new NeverEqual();

        @Override
        public boolean equivalent(final @Nullable Object a, final @Nullable Object b) {
            return false;
        }
    }
}
