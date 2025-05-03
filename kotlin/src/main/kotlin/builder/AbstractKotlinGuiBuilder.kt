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
package dev.triumphteam.gui.kotlin.builder

import dev.triumphteam.gui.BaseGui
import dev.triumphteam.gui.actions.GuiCloseAction
import dev.triumphteam.gui.builder.BaseGuiBuilder
import dev.triumphteam.gui.click.handler.ClickHandler
import dev.triumphteam.gui.component.functional.FunctionalGuiComponent
import dev.triumphteam.gui.component.functional.FunctionalGuiComponentRender
import dev.triumphteam.gui.component.functional.SimpleFunctionalGuiComponent
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer
import dev.triumphteam.gui.container.GuiContainer
import dev.triumphteam.gui.container.type.GuiContainerType
import dev.triumphteam.gui.element.GuiItem
import dev.triumphteam.gui.layout.GuiLayout
import dev.triumphteam.gui.slot.Slot
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

    public fun onClose(block: GuiCloseAction) {
        backing.onClose(block)
    }

    protected fun backing(): B = backing

    public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(row: Int, column: Int, item: GuiItem<P, I>): Unit =
        setItem(row, column, item)

    public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(slot: Slot, item: GuiItem<P, I>): Unit =
        setItem(slot, item)

    public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(slot: Int, item: GuiItem<P, I>): Unit =
        setItem(slot, item)

    public operator fun <P : Any, I : Any> GuiContainer<P, I>.set(layout: GuiLayout, item: GuiItem<P, I>): Unit =
        fill(layout, item)

    public inline fun <P : Any, I : Any, T : Any> GuiContainer<P, I>.fill(
        layout: GuiLayout,
        elements: Collection<T>,
        transform: (T) -> GuiItem<P, I>
    ) {
        val elementIterator = elements.iterator()
        for (slot in layout) {
            if (!elementIterator.hasNext()) break
            set(slot, transform(elementIterator.next()))
        }
    }
}
