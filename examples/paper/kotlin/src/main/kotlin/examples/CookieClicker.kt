package dev.triumphteam.gui.example.examples

import dev.triumphteam.gui.kotlin.set
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

public class CookieClicker : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val gui = buildGui {

            containerType = chestContainer {
                rows = 1
            }

            component {

                // Remember how many times the item was clicked
                var clicks by remember(0)

                render { container ->
                    container[1, 1] = ItemBuilder.from(Material.COOKIE)
                        .name(Component.text("Clicked $clicks times!"))
                        .asGuiItem { _, _ ->
                            clicks++
                        }
                }
            }
        }

        gui.open(sender)
        return true
    }
}
