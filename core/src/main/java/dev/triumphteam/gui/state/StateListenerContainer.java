package dev.triumphteam.gui.state;

import dev.triumphteam.gui.BaseGuiView;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A map backed container for state listeners.
 * This container uses a {@link WeakHashMap}, so instances of the {@link BaseGuiView} can prevent
 * values from being garbage collected correctly.
 */
public final class StateListenerContainer {

    private final Set<Pair> listeners = ConcurrentHashMap.newKeySet();

    /**
     * Adds listener tied to the {@link BaseGuiView} lifecycle.
     *
     * @param view     The view to be used as the reference.
     * @param listener The listener to run when a state is triggered.
     */
    public void addListener(final @NotNull BaseGuiView<?, ?> view, final @NotNull Runnable listener) {
        listeners.add(new Pair(new WeakReference<>(view), listener));
    }

    /**
     * Triggers all listeners that this state uses.
     */
    public void triggerAll() {
        listeners.forEach(pair -> pair.listener.run());
    }

    /**
     * Simple pair only used by this class.
     * Simply used to hold a weak reference of the view and a listener.
     *
     * @param weakView The weak reference of view.
     * @param listener The listener to run when a state is triggered.
     */
    private record Pair(WeakReference<BaseGuiView<?, ?>> weakView, Runnable listener) {}
}
