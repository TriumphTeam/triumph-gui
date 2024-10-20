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
