/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
