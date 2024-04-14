package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.component.GuiComponentRenderer;
import dev.triumphteam.gui.component.SimpleGuiComponentRenderer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class BaseGuiBuilder<B extends BaseGuiBuilder<B, P, G, V>, P, G extends BaseGui<P, V>, V extends BaseGuiView<P, V>> {

    protected final List<SimpleGuiComponentRenderer<P, V>> componentRenderers = new ArrayList<>();

    @Contract("_ -> this")
    public @NotNull B component(final @NotNull Consumer<@NotNull GuiComponentRenderer<@NotNull P, @NotNull V>> renderer) {
        final var componentRenderer = new SimpleGuiComponentRenderer<P, V>();
        renderer.accept(componentRenderer);
        componentRenderers.add(componentRenderer);
        return (B) this;
    }

    public abstract G build();
}
