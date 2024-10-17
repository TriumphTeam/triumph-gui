package dev.triumphteam.gui.title;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface ReactiveGuiTitle extends StatefulGuiTitle {

    @NotNull Component render();
}
