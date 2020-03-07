package me.mattstudios.mfgui.gui;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Command("test")
public final class CMD extends CommandBase {

    @Default
    public void open(Player player) {
        GUI gui = new GUI(Main.fuck, 5, "");

        gui.fillBorder(Arrays.asList(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)), new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))));

        gui.open(player);
    }

}
