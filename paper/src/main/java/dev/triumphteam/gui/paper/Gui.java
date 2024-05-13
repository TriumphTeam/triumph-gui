package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.paper.builder.gui.GuiBuilder;
import dev.triumphteam.gui.paper.container.type.ChestContainerType;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The GUI implementation for Paper servers.
 */
public final class Gui implements BaseGui<Player> {

    static {
        GuiBukkitListener.register();
    }

    private final Component title;
    private final List<GuiComponent<Player, ItemStack>> components;
    private final PaperContainerType containerType;
    private final GuiComponentRenderer<Player, ItemStack> componentRenderer;
    private final ClickHandler<Player> clickHandler;
    private final long spamPreventionDuration;

    public Gui(
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

    /**
     * Create a new {@link GuiBuilder} to create a new {@link Gui}.
     *
     * @param type The {@link GuiContainerType} to be used.
     * @return A new {@link GuiBuilder}.
     */
    @Contract("_ -> new")
    public static GuiBuilder of(final @NotNull GuiContainerType type) {
        return new GuiBuilder(type);
    }

    /**
     * Create a new {@link GuiBuilder} to create a new {@link Gui}.
     * This factory will default to using a {@link ChestContainerType}.
     *
     * @param rows The rows of the {@link ChestContainerType}.
     * @return A new {@link GuiBuilder}.
     */
    @Contract("_ -> new")
    public static GuiBuilder of(final int rows) {
        return new GuiBuilder(new ChestContainerType(rows));
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
