package me.mattstudios.mfgui.gui;

import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main fuck;

    @Override
    public void onEnable() {
        fuck = this;

        new CommandManager(this).register(new CMD());
    }
}
