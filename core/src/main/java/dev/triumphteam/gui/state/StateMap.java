package dev.triumphteam.gui.state;

import dev.triumphteam.gui.BaseGuiView;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.WeakHashMap;

public final class StateMap {

    private final Map<BaseGuiView<?, ?>, Runnable> backing = new WeakHashMap<>();

    public void put(final @NotNull BaseGuiView<?, ?> view, final @NotNull Runnable runnable) {
        backing.put(view, runnable);
    }

    public void run() {
        backing.forEach((inventoryInterface, runnable) -> runnable.run());
    }
}
