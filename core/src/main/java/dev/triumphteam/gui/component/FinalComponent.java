package dev.triumphteam.gui.component;

import dev.triumphteam.gui.state.State;

import java.util.List;

public record FinalComponent<P, I>(
    List<State<?>> states,
    GuiComponentRender<P, I> component
) {
}
