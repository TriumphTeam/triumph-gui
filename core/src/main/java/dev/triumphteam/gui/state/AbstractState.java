package dev.triumphteam.gui.state;

import dev.triumphteam.gui.GuiView;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractState implements State {

    private final StateListenerContainer listenerContainer = new StateListenerContainer();

    @Override
    public void trigger() {
        listenerContainer.triggerAll();
    }

    @Override
    public void addListener(final @NotNull GuiView<?, ?> view, final @NotNull Runnable listener) {
        listenerContainer.addListener(view, listener);
    }
}
