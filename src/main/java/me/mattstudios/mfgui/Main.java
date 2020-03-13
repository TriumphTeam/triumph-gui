package me.mattstudios.mfgui;

import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandManager(this).register(new CMD(this));
    }

}
