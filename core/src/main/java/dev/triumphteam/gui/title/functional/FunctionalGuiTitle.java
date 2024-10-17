package dev.triumphteam.gui.title.functional;

import dev.triumphteam.nova.holder.StateHolder;
import org.jetbrains.annotations.NotNull;

public interface FunctionalGuiTitle extends StateHolder {

    void render(final @NotNull FunctionalGuiTitleRender render);
}
