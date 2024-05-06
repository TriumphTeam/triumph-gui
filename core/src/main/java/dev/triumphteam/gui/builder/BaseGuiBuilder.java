package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.Gui;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentBuilder;
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class BaseGuiBuilder<B extends BaseGuiBuilder<B, P, G, I>, P, G extends Gui<P>, I> {

    protected final List<GuiComponent<P, I>> components = new ArrayList<>();

    @Contract("_ -> this")
    public @NotNull B component(final @NotNull FunctionalGuiComponentBuilder<P, I> builder) {
        final var componentRenderer = new SimpleFunctionalGuiComponent<P, I>();
        builder.accept(componentRenderer);
        components.add(componentRenderer.asGuiComponent());
        return (B) this;
    }

    @Contract("_ -> this")
    public @NotNull B component(final @NotNull GuiComponent<P, I> component) {
        components.add(component);
        return (B) this;
    }

    public abstract G build();
}
