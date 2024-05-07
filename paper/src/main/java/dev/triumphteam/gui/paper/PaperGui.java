package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.Gui;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PaperGui implements Gui<Player> {

    static {
        GuiBukkitListener.register();
    }

    private final Component title;
    private final List<GuiComponent<Player, ItemStack>> components;
    private final PaperContainerType containerType;
    private final GuiComponentRenderer<Player, ItemStack> componentRenderer;
    private final ClickHandler<Player> clickHandler;
    private final long spamPreventionDuration;

    public PaperGui(
        final @NotNull Component title,
        final @NotNull List<GuiComponent<Player, ItemStack>> components,
        final @NotNull PaperContainerType containerType,
        final @NotNull GuiComponentRenderer<Player, ItemStack> componentRenderer,
        final @NotNull ClickHandler<Player> clickHandler,
        final long spamPreventionDuration
    ) {
        this.title = title;
        this.components = components;
        this.containerType = containerType;
        this.componentRenderer = componentRenderer;
        this.clickHandler = clickHandler;
        this.spamPreventionDuration = spamPreventionDuration;
    }

    @Override
    public void open(final @NotNull Player player) {
        final var view = new BukkitGuiView(
            player,
            title,
            containerType,
            components,
            componentRenderer,
            clickHandler,
            spamPreventionDuration
        );

        view.open();
    }
}
