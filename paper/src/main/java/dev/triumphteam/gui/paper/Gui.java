package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Gui implements dev.triumphteam.gui.Gui<Player> {

    static {
        GuiBukkitListener.register();
    }

    private final List<GuiComponent<Player, ItemStack>> components;

    public Gui(final @NotNull List<SimpleFunctionalGuiComponent<Player, ItemStack>> componentRenderers) {
        this.components = componentRenderers.stream().map(SimpleFunctionalGuiComponent::asGuiComponent).toList();
    }

    @Override
    public void open(final @NotNull Player player) {
        final var view = new BukkitGuiView(player, components);
        view.open();
    }
}
