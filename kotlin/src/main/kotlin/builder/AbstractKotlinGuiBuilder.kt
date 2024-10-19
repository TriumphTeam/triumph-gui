package dev.triumphteam.gui.kotlin.builder

import dev.triumphteam.gui.BaseGui
import dev.triumphteam.gui.builder.BaseGuiBuilder
import dev.triumphteam.gui.click.handler.ClickHandler
import dev.triumphteam.gui.component.functional.FunctionalGuiComponent
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentRender
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer
import dev.triumphteam.gui.container.type.GuiContainerType
import dev.triumphteam.gui.title.functional.FunctionalGuiTitle
import dev.triumphteam.gui.title.functional.SimpleFunctionalGuiTitle
import net.kyori.adventure.text.Component
import kotlin.time.Duration

public abstract class AbstractKotlinGuiBuilder<B : BaseGuiBuilder<B, P, G, I, C>, P, G : BaseGui<P>, I, C : GuiContainerType>(
    @PublishedApi internal val backing: B,
) {

    public var containerType: C? = null
        set(value) {
            field = value
            value?.let(backing::containerType)
        }

    public var spamPreventionDuration: Duration? = null
        set(value) {
            field = value
            backing.spamPreventionDuration(value?.inWholeMilliseconds ?: -1)
        }

    public var clickHandler: ClickHandler<P>? = null
        set(value) {
            field = value
            value?.let(backing::clickHandler)
        }

    public var componentRenderer: GuiComponentRenderer<P, I>? = null
        set(value) {
            field = value
            value?.let(backing::componentRenderer)
        }

    public fun title(title: Component) {
        backing.title(title)
    }

    public inline fun title(block: FunctionalGuiTitle.() -> Unit) {
        backing.title(SimpleFunctionalGuiTitle().apply(block).asGuiTitle())
    }

    public fun statelessComponent(render: FunctionalGuiComponentRender<P, I>) {
        backing.statelessComponent(render)
    }

    public inline fun component(block: FunctionalGuiComponent<P, I>.() -> Unit) {
        backing.component(SimpleFunctionalGuiComponent<P, I>().apply(block).asGuiComponent())
    }

    protected fun backing(): B = backing
}
