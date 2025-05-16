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

import dev.triumphteam.gui.click.MoveResult;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.builder.item.ItemBuilder;
import dev.triumphteam.gui.slot.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                .usePlayerInventory()
                .title(Component.text("Static Gui")) // Simple title for the GUI
                .statelessComponent(container -> { // A stateless component, we don't care about the item updating, just want the click action
                    container.setItem(1, 1, ItemBuilder.from(Material.PAPER)
                            .name(Component.text("My Paper"))
                            .asGuiItem((player, context) -> {
                                player.sendMessage("You have clicked on the paper item!");
                            })
                    );
                })
                .statelessComponent(container -> {
                    GuiLayout.border(6).forEach(slot -> {
                        container.setAction(slot, GuiClickAction.movable((player, context) -> {
                            player.sendMessage(Component.text("You CANNOT place/pickup items here!").color(NamedTextColor.RED));
                            return MoveResult.DISALLOW;
                        }));
                    });

                    GuiLayout.box(Slot.of(2, 2), Slot.of(5, 8)).forEach(slot -> {
                        container.setAction(slot, GuiClickAction.movable((player, context) -> {
                            player.sendMessage(Component.text("Clicked on GUI").color(NamedTextColor.GREEN));
                            return MoveResult.ALLOW;
                        }));
                    });

                    GuiLayout.box(Slot.of(7, 2), Slot.of(10, 8)).forEach(slot -> {
                        container.setItem(slot, ItemBuilder.from(Material.PAPER).asGuiItem(GuiClickAction.movable((player, context) -> {
                            return MoveResult.ALLOW;
                        })));
                    });
                })
                .build();

        gui.open(senderPlayer);
        // Gui.openFor(senderPlayer);
        return true;
    }
}
