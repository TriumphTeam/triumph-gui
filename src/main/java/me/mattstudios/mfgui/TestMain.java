package me.mattstudios.mfgui;

import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class TestMain extends JavaPlugin implements CommandExecutor {

    private GUI gui;
    private long test = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

    public void onEnable() {
        // Maybe?
        gui = new GUI(this, 2, "test");
        Objects.requireNonNull(getCommand("gui")).setExecutor(this);

        gui.setFillItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), event -> event.setCancelled(true)));
        gui.setOpenGuiAction(event -> event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1f, .5f));
        gui.setCloseGuiAction(event -> event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1f, .5f));

        gui.setItem(0, new GuiItem(new ItemStack(Material.DIAMOND)));
        gui.setItem(1, new GuiItem(new ItemStack(Material.AIR)));

        gui.addSlotAction(1, event -> {
            event.setCancelled(true);
            gui.updateItem(1, event.getCursor());
        });

        gui.setItem(1, 5, new GuiItem(new ItemStack(Material.OAK_WOOD), event -> {
            event.setCancelled(true);
            ((GUI) event.getInventory().getHolder()).getGuiItem(event.getSlot()).setItemStack(event.getCursor());
            event.getWhoClicked().sendMessage("Clicking on WOOD");
        }));
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player) sender;

        if (player.getName().equalsIgnoreCase("LichtHund")) {
            System.out.println("true");
            test -= 5;
        }

        gui.open(player);

        return true;
    }
}
