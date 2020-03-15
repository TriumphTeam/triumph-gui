package me.mattstudios.mfgui.gui.guis;

import org.bukkit.plugin.Plugin;

public final class GUI extends BaseGui {

    public GUI(final Plugin plugin, final int rows, final String title) {
        super(plugin, rows, title);
    }

    public GUI(final Plugin plugin, final String title) {
        super(plugin, title);
    }

}
