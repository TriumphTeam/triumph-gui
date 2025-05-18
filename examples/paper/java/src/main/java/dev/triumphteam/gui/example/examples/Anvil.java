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

import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.builder.item.ItemBuilder;
import dev.triumphteam.gui.slot.Slot;
import dev.triumphteam.nova.MutableState;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Anvil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (!(sender instanceof Player senderPlayer)) return false;

// Just a list of all valid item materials.
        final List<Material> materials = Arrays.stream(Material.values()).filter(Material::isItem).toList();

// State to handle the input.
        final MutableState<String> inputState = MutableState.of("");

        final var gui = Gui
                .anvil(inputState::set) // We set the input state here.
                .usePlayerInventory()
                .title(Component.text("Anvil Gui"))
                .statelessComponent(container -> {
                    // Input item
                    container.setItem(Slot.Anvil.primaryInput(), ItemBuilder.from(Material.DIAMOND).asGuiItem());

                    // Result item
                    container.setItem(Slot.Anvil.result(), ItemBuilder.from(Material.NAME_TAG).asGuiItem());

                    // These 2 are needed
                })
                .component(component -> {
                    component.remember(inputState); // Remember the input state.

                    component.render(container -> {

                        // Using iterator to be easier.
                        final Iterator<Material> filtered;
                        if (inputState.get().isEmpty()) { // if nothing, we show everything.
                            filtered = materials.iterator();
                        } else { // If there is an input, then we filter the items.
                            filtered = materials.stream()
                                    .filter(material -> material.name().toLowerCase().contains(inputState.get().toLowerCase()))
                                    .toList()
                                    .iterator();
                        }

                        // A box around the player's inventory.
                        GuiLayout.box(Slot.of(1, 1), Slot.of(3, 9)).forEach(slot -> {
                            if (!filtered.hasNext()) return; // No items just ignore.
                            final Material material = filtered.next();

                            // Add item.
                            container.setItem(slot, ItemBuilder.from(material).asGuiItem());
                        });
                    });
                })
                .build();

        gui.open(senderPlayer);
        // Gui.openFor(senderPlayer);
        return true;
    }
}
