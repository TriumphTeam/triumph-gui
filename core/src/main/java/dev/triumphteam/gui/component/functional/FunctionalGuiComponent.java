package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to a {@link GuiComponent} this component will take in states and render a component.
 * Unlike {@link GuiComponent} it is not meant to be extended upon and is only used by the {@link BaseGuiBuilder}.
 *
 * @param <P> The player type.
 * @param <I> The item type.
 */
public interface FunctionalGuiComponent<P, I> extends FunctionalStateContainer {

    void clickHandler(final @Nullable ClickHandler<P> clickHandler);

    /**
     * A component render function.
     * The function inside works the same as a normal {@link ReactiveGuiComponent#render(GuiContainer, Object)} would.
     *
     * @param render The component render.
     */
    void render(final @NotNull GuiComponentRender<@NotNull P, @NotNull I> render);
}
