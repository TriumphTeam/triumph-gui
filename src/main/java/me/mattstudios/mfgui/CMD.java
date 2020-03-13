package me.mattstudios.mfgui;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.guis.PersistentBaseGui;
import org.bukkit.entity.Player;

@Command("cmd")
public final class CMD extends CommandBase {

    private final Main plugin;

    private final PersistentBaseGui gui;

    public CMD(final Main plugin) {
        this.plugin = plugin;
        this.gui = new PersistentBaseGui(plugin, 5, "");

    }

    @Default
    public void test(final Player player) {
        gui.test();
        gui.open(player);
    }

}
