package dev.triumphteam.gui.component;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.state.State;

import java.util.List;

public record FinalComponent<P, V extends BaseGuiView<P, V>>(List<State<?>> states, GuiComponent<P, V> component) {
}
