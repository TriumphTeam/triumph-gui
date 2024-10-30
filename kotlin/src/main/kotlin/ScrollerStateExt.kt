package dev.triumphteam.gui.kotlin

import dev.triumphteam.gui.layout.GuiLayout
import dev.triumphteam.gui.state.pagination.PagerState
import dev.triumphteam.gui.state.pagination.ScrollerState
import dev.triumphteam.gui.state.pagination.SimplePagerState
import dev.triumphteam.gui.state.pagination.SimpleScrollerState

public fun <T> pagerState(
    startPage: Int = 1,
    elements: List<T>,
    layout: GuiLayout,
): PagerState<T> =
    SimplePagerState(startPage, elements, layout)

public fun <T> scrollerState(
    steps: Int,
    elements: List<T>,
    layout: GuiLayout,
): ScrollerState<T> =
    SimpleScrollerState(steps, elements, layout)
