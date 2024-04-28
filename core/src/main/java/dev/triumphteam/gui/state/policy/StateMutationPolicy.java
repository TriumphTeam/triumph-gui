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
