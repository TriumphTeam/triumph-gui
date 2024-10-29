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

import dev.triumphteam.gui.kotlin.set
import dev.triumphteam.gui.kotlin.slot
import dev.triumphteam.gui.layout.GuiLayout
import dev.triumphteam.gui.paper.builder.item.ItemBuilder
import dev.triumphteam.gui.paper.kotlin.builder.buildGui
import dev.triumphteam.gui.paper.kotlin.builder.chestContainer
import dev.triumphteam.gui.slot.Slot
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.time.Duration.Companion.seconds

private class WeirdLayout : GuiLayout {

    private val slots = buildList {
        (3..7).forEach { add(slot(1, it)) }
        add(slot(2, 1))
        add(slot(2, 2))
        add(slot(2, 5))
        add(slot(2, 6))
        add(slot(2, 9))
        (5..9).forEach { add(slot(3, it)) }
    }.toMutableList()

    override fun iterator(): MutableIterator<Slot> {
        return slots.iterator()
    }

    override fun size(): Int {
        return slots.size
    }
}

public class Static : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return false

        val gui = buildGui {
            // If not set, defaults to chest - 1 row
            containerType = chestContainer {
                rows = 6
            }

            spamPreventionDuration = 0.seconds

            // Simple title for the gui
            title(Component.text("Static Gui"))

            /*// A stateless component, we don't care about the item updating, just want the click action
            statelessComponent { container ->
                container[1, 1] = ItemBuilder.from(Material.PAPER)
                    .name(Component.text("My Paper"))
                    .asGuiItem { player, _ ->
                        player.sendMessage("You have clicked on the paper item!")
                    }
            }*/

            component {

                // Random list with all materials
                // val materials = Material.entries.filter { it.isItem && !it.isAir }.shuffled()

                val pageState = rememberPager(
                    (1..200).toList(),
                    GuiLayout.box(slot(1, 2), slot(3, 8)),
                )

                render { container ->

                    // Loop through the current page
                    pageState.forEach { (slot, element) ->
                        // Create item from material in the page
                        container[slot] =
                            ItemBuilder.from(Material.PAPER).name(Component.text("Element -> $element")).asGuiItem()
                    }

                    // previous button
                    container[5, 2] = ItemBuilder.from(Material.PAPER).name(Component.text("Previous"))
                        .asGuiItem { _, _ ->
                            pageState.prev()
                        }

                    // next button
                    container[5, 8] = ItemBuilder.from(Material.PAPER).name(Component.text("Next"))
                        .asGuiItem { _, _ ->
                            pageState.next()
                        }
                }
            }
        }

        gui.open(sender)
        return true
    }
}
