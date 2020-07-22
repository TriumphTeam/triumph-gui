package me.mattstudios.mfgui.gui.guis;

import me.mattstudios.mfgui.gui.components.GuiType;
import org.bukkit.plugin.Plugin;

/**
 * Standard GUI with different types
 */
public final class Gui extends BaseGui {

    public Gui(final Plugin plugin, final int rows, final String title) {
        super(rows, title);
    }

    public Gui(final Plugin plugin, final String title) {
        super(title);
    }

    public Gui(final Plugin plugin, final GuiType guiType, final String title) {
        super(guiType, title);
    }

}
