package dev.triumphteam.gui.title;

import dev.triumphteam.gui.title.functional.FunctionalGuiTitleRender;
import dev.triumphteam.nova.State;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SimpleGuiTitle implements ReactiveGuiTitle {

    private final List<State> states;
    private final FunctionalGuiTitleRender render;

    public SimpleGuiTitle(final @NotNull FunctionalGuiTitleRender render, final @NotNull List<State> states) {
        this.render = render;
        this.states = states;
    }

    @Override
    public @NotNull Component render() {
        return render.render();
    }

    @Override
    public @NotNull List<State> states() {
        return states;
    }
}
