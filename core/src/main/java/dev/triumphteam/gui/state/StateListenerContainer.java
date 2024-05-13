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
