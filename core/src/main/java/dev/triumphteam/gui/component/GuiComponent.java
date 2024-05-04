package dev.triumphteam.gui.component;

import dev.triumphteam.gui.click.handler.ClickHandler;
import org.jetbrains.annotations.Nullable;

public interface GuiComponent<P, I> {

    @Nullable
    default ClickHandler<P> clickHandler() {
        return null;
    }
}
