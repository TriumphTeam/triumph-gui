package dev.triumphteam.gui.kotlin

import dev.triumphteam.gui.slot.Slot

public operator fun Slot.component1(): Int = row
public operator fun Slot.component2(): Int = column

public fun slot(row: Int, column: Int): Slot = Slot(row, column)
