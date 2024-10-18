package dev.triumphteam.gui.kotlin

import dev.triumphteam.gui.container.GuiContainer
import dev.triumphteam.gui.item.GuiItem
import dev.triumphteam.gui.layout.GuiLayout
import dev.triumphteam.gui.slot.Slot

public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(row: Int, column: Int, item: GuiItem<P, I>): Unit =
    setItem(row, column, item)

public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(slot: Slot, item: GuiItem<P, I>): Unit =
    setItem(slot, item)

public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(slot: Int, item: GuiItem<P, I>): Unit =
    setItem(slot, item)

public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(layout: GuiLayout, item: GuiItem<P, I>): Unit =
    fill(layout, item)
