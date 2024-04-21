package dev.triumphteam.gui.state;

public interface MutableState<T> extends State {

    T getValue();

    void setValue(final T value);
}
