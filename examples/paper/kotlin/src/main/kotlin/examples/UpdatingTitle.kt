package dev.triumphteam.gui.example.examples

import dev.triumphteam.gui.kotlin.set
import dev.triumphteam.gui.paper.builder.item.ItemBuilder
import dev.triumphteam.gui.paper.kotlin.builder.buildGui
import dev.triumphteam.nova.getValue
import dev.triumphteam.nova.mutableStateOf
import dev.triumphteam.nova.setValue
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

public class UpdatingTitle : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val gui = buildGui {

            // Firstly, we create a state that'll be used by the title
            // It's created here and not directly in the "remember" because it needs to be accessible in the component too
            val titleState = mutableStateOf(Component.text("Original title"))

            // Delegate the value, so it's nicer to use it
            // This isn't really necessary
            var title by titleState

            title {
                // Tell the title to remember the state
                remember(titleState)
                // Render the title's value
                render { title }
            }

            // Since we don't care about updating the items, we can just use a stateless component
            statelessComponent { container ->
                // First item to change the title to PAPER
                container[1, 1] = ItemBuilder.from(Material.PAPER)
                    .name(Component.text("Change title to 'PAPER TITLE'!"))
                    .asGuiItem { _, _ ->
                        title = Component.text("PAPER TITLE") // Triggers an update
                    }

                // Second item to change title to COOKIE
                container[1, 2] = ItemBuilder.from(Material.COOKIE)
                    .name(Component.text("Change title to 'COOKIE TITLE'!"))
                    .asGuiItem { _, _ ->
                        title = Component.text("COOKIE TITLE") // Triggers an update
                    }
            }

            // Important to remember; due to by default states using "StateMutabilityPolicy.StructuralEquality"
            // it means that if the title is the same as the new value it'll **NOT** trigger an update
        }

        gui.open(sender)
        return true
    }
}
