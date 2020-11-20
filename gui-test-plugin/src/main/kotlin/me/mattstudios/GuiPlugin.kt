package me.mattstudios

import me.mattstudios.annotations.BukkitPlugin
import me.mattstudios.gui.components.util.ItemBuilder
import me.mattstudios.gui.guis.BaseGui
import me.mattstudios.gui.guis.PaginatedGui
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

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

    @Default
    fun gui(player: Player) {
        val gui = PaginatedGui(6, "Test GUI")
        gui.setDefaultClickAction { it.isCancelled = true }
        val fillerItem = ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem()
        gui.filler.fillBorder(fillerItem)

        val materials = Material.values().filter { it.isItem }
        repeat(500) {
            gui.addItem(ItemBuilder.from(materials.random()).asGuiItem())
        }

        val prev = ItemBuilder.from(Material.PAPER).asGuiItem()
        val next = ItemBuilder.from(Material.PAPER).asGuiItem()

        prev.setAction {
            gui.previous()

            if (gui.currentPageNum == 1) gui.updateItem(47, fillerItem)
            else gui.updateItem(47, prev)

            if (gui.currentPageNum == gui.pagesNum) gui.updateItem(51, fillerItem)
            else gui.updateItem(51, next)
        }

        next.setAction {
            gui.next()

            if (gui.currentPageNum == 1) gui.updateItem(47, fillerItem)
            else gui.updateItem(47, prev)

            if (gui.currentPageNum == gui.pagesNum) gui.updateItem(51, fillerItem)
            else gui.updateItem(51, next)
        }

        gui.setItem(47, fillerItem)
        gui.setItem(51, next)

        gui.open(player)
    }

}