package dev.triumphteam.gui.example.examples

import dev.triumphteam.gui.kotlin.set
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

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
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
