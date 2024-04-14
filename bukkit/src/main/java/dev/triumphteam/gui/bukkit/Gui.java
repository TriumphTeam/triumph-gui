package dev.triumphteam.gui.bukkit;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.component.SimpleGuiComponentRenderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class Gui implements BaseGui<Player, GuiView> {

    private final List<SimpleGuiComponentRenderer<Player>> componentRenderers;

    public Gui(
        final @NotNull List<SimpleGuiComponentRenderer<Player>> componentRenderers
    ) {
        this.componentRenderers = componentRenderers;
    }

    @Override
    public @NotNull GuiView open(final @NotNull Player player, final @Nullable GuiView parent) {
        final var view = new GuiView(player, parent);
        view.open();
        return view;
    }
}
