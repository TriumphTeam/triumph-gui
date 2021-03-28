package me.mattstudios

import me.mattstudios.annotations.BukkitPlugin
import me.mattstudios.gui.components.util.ItemBuilder
import me.mattstudios.gui.components.util.ItemNBT
import me.mattstudios.gui.guis.Gui
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
        val status = true
        val gui = Gui(6, "Hello")

        gui.setDefaultTopClickAction { event ->
            event.isCancelled = true
        }

        val item = ItemBuilder.from(Material.WRITABLE_BOOK)
            .setName("Name")
            .asGuiItem()

        item.setAction {
            val itemStack = ItemBuilder.from(item.itemStack).removeNbt("mf-gui").build()
            println(ItemNBT.getTag(ItemNBT.asNMSCopy(itemStack)))
            gui.updateItem(1, itemStack)
            player.sendMessage("updated")
        }

        gui.setItem(1, item)
        gui.open(player)
    }

    private fun buildSettingItem(): ItemStack {
        return ItemBuilder.from(Material.WRITABLE_BOOK)
            .setName("Name")
            .build()
    }

}