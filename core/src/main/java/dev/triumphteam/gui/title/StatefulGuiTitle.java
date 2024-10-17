package dev.triumphteam.gui.title;

import dev.triumphteam.nova.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface StatefulGuiTitle extends GuiTitle {

    @NotNull List<State> states();
}
