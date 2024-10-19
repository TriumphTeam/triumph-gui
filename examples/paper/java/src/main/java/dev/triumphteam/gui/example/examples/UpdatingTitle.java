package dev.triumphteam.gui.example.examples;

import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.builder.item.ItemBuilder;
import dev.triumphteam.nova.MutableState;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpdatingTitle implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (!(sender instanceof Player senderPlayer)) return false;

        // Firstly, we create a state that'll be used by the title
        // It's created here and not directly in the "remember" because it needs to be accessible in the component too
        final var titleState = MutableState.of(Component.text("Original title"));

        final var gui = Gui
            .of(1)
            .title((title) -> {
                // Tell the title to remember the state
                title.remember(titleState);
                // Render the title's value
                title.render(titleState::get);
            })
            .statelessComponent(container -> {  // Since we don't care about updating the items, we can just use a stateless component

                // First item to change the title to PAPER
                container.setItem(1, 1, ItemBuilder.from(Material.PAPER)
                    .name(Component.text("Change title to 'PAPER TITLE'!"))
                    .asGuiItem((player, context) -> {
                        titleState.set(Component.text("PAPER TITLE")); // Triggers an update
                    })
                );

                // Second item to change title to COOKIE
                container.setItem(1, 2, ItemBuilder.from(Material.COOKIE)
                    .name(Component.text("Change title to 'COOKIE TITLE'!"))
                    .asGuiItem((player, context) -> {
                        titleState.set(Component.text("COOKIE TITLE")); // Triggers an update
                    })
                );

                // Important to remember; due to by default states using "StateMutabilityPolicy.StructuralEquality"
                // it means that if the title is the same as the new value it'll **NOT** trigger an update
            })
            .build();

        gui.open(senderPlayer);
        return true;
    }
}
