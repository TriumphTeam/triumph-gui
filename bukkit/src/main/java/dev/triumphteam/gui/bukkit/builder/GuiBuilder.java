package dev.triumphteam.gui.bukkit.builder;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.bukkit.Gui;
import dev.triumphteam.gui.bukkit.GuiView;
import org.bukkit.entity.Player;

public final class GuiBuilder extends BaseGuiBuilder<GuiBuilder, Player, Gui, GuiView> {

    @Override
    public Gui build() {
        return new Gui(componentRenderers);
    }
}
