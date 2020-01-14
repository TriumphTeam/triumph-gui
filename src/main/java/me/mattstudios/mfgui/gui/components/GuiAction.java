package me.mattstudios.mfgui.gui.components;

@FunctionalInterface
public interface GuiAction<T> {

    /**
     * Executes the task passed to it
     *
     * @param event Inventory action
     */
    void execute(final T event);
}
