package dev.triumphteam.gui.component;

import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface StatefulGuiComponent<P, I> extends GuiComponent<P, I> {

    @NotNull
    List<State> states();
}
