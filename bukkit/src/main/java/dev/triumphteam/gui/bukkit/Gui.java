package dev.triumphteam.gui.bukkit;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.component.SimpleFunctionalGuiComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Gui implements BaseGui<Player> {

    private final List<FinalComponent<Player, ItemStack>> components;

    static {
        GuiBukkitListener.register();
    }

    public Gui(
        final @NotNull List<SimpleFunctionalGuiComponent<Player, ItemStack>> componentRenderers
    ) {
        this.components = componentRenderers.stream().map(SimpleFunctionalGuiComponent::createComponent).toList();
    }

    @Override
    public void open(final @NotNull Player player) {
        final var view = new GuiView(player, components);
        view.open();
    }
}
