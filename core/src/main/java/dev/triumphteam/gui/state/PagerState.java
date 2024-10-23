package dev.triumphteam.gui.state;

public interface PagerState<T> extends ScrollableState<T> {

    int getCurrentPage();

    int getPageCount();
}
