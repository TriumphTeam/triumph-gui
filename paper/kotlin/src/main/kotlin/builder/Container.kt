package dev.triumphteam.gui.paper.kotlin.builder

import dev.triumphteam.gui.paper.container.type.ChestContainerType
import dev.triumphteam.gui.paper.container.type.PaperContainerType

public class ContainerBuilder @PublishedApi internal constructor() {
    public var rows: Int = 1
}

public inline fun chestContainer(block: ContainerBuilder.() -> Unit): PaperContainerType {
    return ChestContainerType(ContainerBuilder().apply(block).rows)
}
