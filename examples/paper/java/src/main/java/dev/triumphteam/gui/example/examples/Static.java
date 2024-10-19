package dev.triumphteam.gui.example.examples;

import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Static implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (!(sender instanceof Player senderPlayer)) return false;

        final var gui = Gui
            .of(6)
            .title(Component.text("Static Gui")) // Simple title for the GUI
            .statelessComponent(container -> { // A stateless component, we don't care about the item updating, just want the click action
                container.setItem(1, 1, ItemBuilder.from(Material.COOKIE)
                    .name(Component.text("Clicked $clicks times!"))
                    .asGuiItem((player, context) -> {
                        player.sendMessage("You have clicked on the paper item!");
                    })
                );
            })
            .build();

        gui.open(senderPlayer);
        return true;
    }
}
