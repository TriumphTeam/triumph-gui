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
