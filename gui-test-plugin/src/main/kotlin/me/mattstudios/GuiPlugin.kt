package me.mattstudios

import me.mattstudios.annotations.BukkitPlugin
import me.mattstudios.gui.components.util.ItemBuilder
import me.mattstudios.gui.components.xseries.XMaterial
import me.mattstudios.gui.guis.BaseGui
import me.mattstudios.gui.guis.Gui
import me.mattstudios.gui.guis.GuiItem
import me.mattstudios.gui.guis.PaginatedGui
import me.mattstudios.gui.guis.PersistentGui
import me.mattstudios.gui.guis.ScrollingGui
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.command.CommandSender

import me.mattstudios.mf.annotations.SubCommand
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.event.inventory.InventoryType


/**
 * @author Matt
 */
@BukkitPlugin
class GuiPlugin : JavaPlugin() {

    override fun onEnable() {
        CommandManager(this).register(GuiCommand())
    }

}

@Command("gui")
class GuiCommand : CommandBase() {

    private val items = mutableListOf<Material>()

    init {
        val materials = Material.values().filter { it.isItem }
        repeat(500) {
            items.add(materials.random())
        }
    }

    @Default
    fun gui(player: Player) {
        val gui = ScrollingGui(6, "Hello")
        gui.setDefaultClickAction { it.isCancelled = true }
        gui.filler.fillBorder(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem())

        val materials = Material.values().filter { it.isItem }

        gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).asGuiItem { gui.previous() })
        gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).asGuiItem { gui.next() })

        repeat(500) {
            val item = ItemBuilder.from(materials.random()).asGuiItem { event ->
                player.sendMessage("changing")
                gui.updatePageItem(event.slot, ItemBuilder.from(Material.LIME_CONCRETE).asGuiItem {
                    player.sendMessage("Confirming!!")
                })
            }
            gui.addItem(item)
        }

        gui.open(player)
    }

}