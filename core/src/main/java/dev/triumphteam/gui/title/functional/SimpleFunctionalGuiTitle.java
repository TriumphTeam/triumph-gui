package dev.triumphteam.gui.title.functional;

import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.title.GuiTitle;
import dev.triumphteam.gui.title.SimpleGuiTitle;
import dev.triumphteam.nova.holder.AbstractStateHolder;
import org.jetbrains.annotations.NotNull;

public final class SimpleFunctionalGuiTitle extends AbstractStateHolder implements FunctionalGuiTitle {

    private FunctionalGuiTitleRender guiTitleRender = null;

    @Override
    public void render(final @NotNull FunctionalGuiTitleRender render) {
        this.guiTitleRender = render;
    }

    public @NotNull GuiTitle asGuiTitle() {
        if (guiTitleRender == null) {
            throw new TriumphGuiException("TODO TITLE");
        }
        return new SimpleGuiTitle(guiTitleRender, getStates());
    }
}
