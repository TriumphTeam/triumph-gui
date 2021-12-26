/*
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
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

package dev.triumphteam.gui.example.command;

import dev.triumphteam.gui.animations.Animation;
import dev.triumphteam.gui.animations.Frame;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ExampleCommand implements CommandExecutor {
    private final Frame first;
    private final Frame second;

    public ExampleCommand() {
        Map<Integer, GuiItem> firstItems = new HashMap<>();
        Map<Integer, GuiItem> secondItems = new HashMap<>();

        firstItems.put(1, ItemBuilder.from(Material.DIAMOND)
                .name(Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Hello World"))
                .asGuiItem());
        secondItems.put(1, ItemBuilder.from(Material.DIAMOND)
                .name(Component.text(ChatColor.WHITE + "" + ChatColor.BOLD + "Hello World"))
                .asGuiItem());

        first = new Frame(firstItems);
        second = new Frame(secondItems);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Gui gui = new Gui(3, ChatColor.GOLD + "" + ChatColor.BOLD + "Example Gui", EnumSet.noneOf(InteractionModifier.class));
        gui.setItem(1, ItemBuilder.from(Material.DIAMOND).asGuiItem());

        Animation animation = Animation.infinite(5,first,second);
        gui.addAnimation(animation);

        gui.open(player);

        return true;
    }

}
