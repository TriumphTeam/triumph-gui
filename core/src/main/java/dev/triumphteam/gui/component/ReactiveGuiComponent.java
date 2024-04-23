package dev.triumphteam.gui.component;

import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ReactiveGuiComponent {

    @NotNull
    List<State> states();
}
