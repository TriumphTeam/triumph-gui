package dev.triumphteam.gui.title.functional;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FunctionalGuiTitleRender {

    @NotNull Component render();
}
