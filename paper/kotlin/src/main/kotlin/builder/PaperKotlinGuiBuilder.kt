package dev.triumphteam.gui.paper.kotlin.builder

import dev.triumphteam.gui.kotlin.builder.AbstractKotlinGuiBuilder
import dev.triumphteam.gui.paper.Gui
import dev.triumphteam.gui.paper.builder.gui.PaperGuiBuilder
import dev.triumphteam.gui.paper.container.type.ChestContainerType
import dev.triumphteam.gui.paper.container.type.PaperContainerType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

public class PaperKotlinGuiBuilder @PublishedApi internal constructor() :
    AbstractKotlinGuiBuilder<PaperGuiBuilder, Player, Gui, ItemStack, PaperContainerType>(
        PaperGuiBuilder(ChestContainerType(1))
    ) {

    @PublishedApi
    internal fun build(): Gui = backing().build()
}

public inline fun buildGui(builder: PaperKotlinGuiBuilder.() -> Unit): Gui {
    return PaperKotlinGuiBuilder().apply(builder).build()
}
