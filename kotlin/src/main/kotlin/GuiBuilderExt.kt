package dev.triumphteam.gui.kotlin

import dev.triumphteam.gui.BaseGui
import dev.triumphteam.gui.builder.BaseGuiBuilder
import dev.triumphteam.gui.component.functional.FunctionalGuiComponent
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent

// TODO(matt): Actually make this suspending
public fun <B : BaseGuiBuilder<B, P, G, I>, P, G : BaseGui<P>, I> BaseGuiBuilder<B, P, G, I>.suspendingComponent(
    block: FunctionalGuiComponent<P, I>.() -> Unit,
): B {
    return component(SimpleFunctionalGuiComponent<P, I>().apply(block).asGuiComponent())
}
