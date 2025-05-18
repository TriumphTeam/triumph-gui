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

import dev.triumphteam.gui.kotlin.slot
import dev.triumphteam.gui.layout.GuiLayout
import dev.triumphteam.gui.paper.builder.item.ItemBuilder
import dev.triumphteam.gui.paper.kotlin.builder.buildGui
import dev.triumphteam.gui.paper.kotlin.builder.chestContainer
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

public class Paginated : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        // Simple example to fill in the pages
        val materials = Material.entries.filter { it.isItem && !it.isAir }.shuffled()

        val gui = buildGui {
            // If not set, defaults to chest - 1 row
            containerType = chestContainer {
                rows = 6
            }

            // Simple title for the gui
            title(Component.text("Paginated Gui"))

            component {

                // Tell the component to remember the pager state with the given elements
                // Elements can be anything, in this case it's the material of the page items
                // If the element is already a fully built ItemStack it'll always be static as it is rendered outside the component
                val pagerState = rememberPager(
                    materials,
                    // The layout is very important as it dictates how the items will be distributed in a page and also
                    // how many items there will be per page
                    GuiLayout.box(slot(2, 3), slot(4, 7))
                )

                render { container ->

                    // Loop through the page and set items to the container
                    pagerState.forEach { (slot, element) ->
                        // Create a new item stack with the material provided by the pager
                        container[slot] = ItemBuilder.from(element).asGuiItem()
                    }

                    // The previous page button
                    container[6, 2] = ItemBuilder.from(Material.PAPER)
                        .name(Component.text("Previous page"))
                        .asGuiItem { _, _ ->
                            pagerState.prev()
                        }

                    // The next page button
                    container[6, 8] = ItemBuilder.from(Material.PAPER)
                        .name(Component.text("Next page"))
                        .asGuiItem { _, _ ->
                            pagerState.next()
                        }
                }
            }
        }

        gui.open(sender)
        return true
    }
}
