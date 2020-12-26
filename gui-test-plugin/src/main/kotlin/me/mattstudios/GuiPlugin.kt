package me.mattstudios

import me.mattstudios.annotations.BukkitPlugin
import me.mattstudios.gui.components.util.ItemBuilder
import me.mattstudios.gui.components.xseries.XMaterial
import me.mattstudios.gui.guis.BaseGui
import me.mattstudios.gui.guis.Gui
import me.mattstudios.gui.guis.GuiItem
import me.mattstudios.gui.guis.PaginatedGui
import me.mattstudios.gui.guis.PersistentGui
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
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

    private val items = mutableListOf<Material>()

    init {
        val materials = Material.values().filter { it.isItem }
        repeat(500) {
            items.add(materials.random())
        }
    }

    @Default
    fun gui(player: Player) {
        val gui: PersistentGui = PersistentGui("Hello")
        gui.setDefaultClickAction { it.isCancelled = true }

        gui.setCloseGuiAction { event ->
            player.sendMessage("closing")
        }

        gui.addItem(listOf(ItemStack(Material.DIAMOND), ItemStack(Material.DIRT)))

        // Creates the item builder like you do
        val itemBuilder = ItemBuilder.from(XMaterial.PAPER.parseMaterial()!!)
        // Name like you do
        itemBuilder.setName("&a&lStaff Chat")
        // The lore, but unlike you it doesn't add each line it's just the main one
        itemBuilder.setLore("&7when you type in chat it automaticly goes to staffchat.")

        // Just for testing if it'll update or not
        var i = 0
        // The main item
        gui.setItem(1, itemBuilder.asGuiItem() {
            gui.close(player, false)
        })

        gui.open(player)
    }

}