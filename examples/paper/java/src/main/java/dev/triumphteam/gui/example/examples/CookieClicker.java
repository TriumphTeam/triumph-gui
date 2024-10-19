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

public final class CookieClicker implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (!(sender instanceof Player senderPlayer)) return false;

        final var gui = Gui
            .of(1)
            .spamPreventionDuration(0) // We want fast clicks so let's not have any spam prevention
            .title(Component.text("Cookie Clicker Gui")) // Simple title for the GUI
            .component(component -> { // A reactive component

                // Remember how many times the item was clicked
                final var clicks = component.remember(0);

                // Rendering the component
                component.render(container -> {
                    container.setItem(1, 1, ItemBuilder.from(Material.COOKIE)
                        .name(Component.text("Clicked " + clicks.get() + " times!"))
                        .asGuiItem((player, context) -> {
                            // Increase clicks which, in turn, updates the state of the component
                            clicks.update((value) -> value + 1);
                        })
                    );
                });
            })
            .build();

        gui.open(senderPlayer);
        return true;
    }
}
