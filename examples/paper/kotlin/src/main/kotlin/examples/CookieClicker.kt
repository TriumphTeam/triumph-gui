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
import dev.triumphteam.nova.getValue
import dev.triumphteam.nova.setValue
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.time.Duration.Companion.milliseconds

public class CookieClicker : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val gui = buildGui {

            // Not needed in this case since it's just using the default value of chest - 1 row
            containerType = chestContainer {
                rows = 1
            }

            // We want fast clicks so let's not have any spam prevention
            spamPreventionDuration = 0.milliseconds

            // Simple title for the GUI
            title(Component.text("Cookie Clicker Gui"))

            // A reactive component
            component {

                // Remember how many times the item was clicked
                var clicks by remember(0)

                // Rendering the component
                render { container ->
                    container[1, 1] = ItemBuilder.from(Material.COOKIE)
                        .name(Component.text("Clicked $clicks times!"))
                        .asGuiItem { _, _ ->
                            // Increase clicks which, in turn, updates the state of the component
                            clicks++
                        }
                }
            }
        }

        gui.open(sender)
        return true
    }
}
