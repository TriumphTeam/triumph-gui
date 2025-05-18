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
package dev.triumphteam.gui.example.examples

import dev.triumphteam.gui.paper.builder.item.ItemBuilder
import dev.triumphteam.gui.paper.kotlin.builder.buildGui
import dev.triumphteam.gui.paper.kotlin.builder.chestContainer
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

public class Static : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val gui = buildGui {
            // If not set, defaults to chest - 1 row
            containerType = chestContainer {
                rows = 6
            }

            // Simple title for the gui
            title(Component.text("Static Gui"))

            // A stateless component, we don't care about the item updating, just want the click action
            statelessComponent { container ->
                container[1, 1] = ItemBuilder.from(Material.PAPER)
                    .name(Component.text("My Paper"))
                    .asGuiItem { player, _ ->
                        player.sendMessage("You have clicked on the paper item!")
                    }
            }
        }

        gui.open(sender)
        return true
    }
}
