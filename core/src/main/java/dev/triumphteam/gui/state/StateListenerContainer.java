package dev.triumphteam.gui.state;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.triumphteam.gui.GuiView;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A map backed container for state listeners.
 * This container uses a map with weak keys, so instances of the {@link GuiView} can prevent
 * values from being garbage collected correctly.
 */
public final class StateListenerContainer {

    /**
     * Listeners cache.
     * The keys of the map are weak.
     * The value of the map is a {@link ConcurrentLinkedQueue}.
     */
    private final Map<GuiView<?, ?>, Queue<Runnable>> listeners = createListenerMap();

    /**
     * Creates a map to be used for the listeners.
     *
     * @return A {@link java.util.concurrent.ConcurrentMap} with weak keys.
     */
    private static Map<GuiView<?, ?>, Queue<Runnable>> createListenerMap() {
        final Cache<GuiView<?, ?>, Queue<Runnable>> cache = CacheBuilder.newBuilder()
            .weakKeys()
            .build();

        return cache.asMap();
    }

    /**
     * Adds listener tied to the {@link GuiView} lifecycle.
     *
     * @param view     The view to be used as the reference.
     * @param listener The listener to run when a state is triggered.
     */
    public void addListener(final @NotNull GuiView<?, ?> view, final @NotNull Runnable listener) {
        listeners.computeIfAbsent(view, ignored -> new ConcurrentLinkedQueue<>()).add(listener);
    }

    /**
     * Triggers all listeners that this state uses.
     */
    public void triggerAll() {
        listeners.values().forEach(listeners -> listeners.forEach(Runnable::run));
    }
}
