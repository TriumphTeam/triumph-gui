package dev.triumphteam.gui.paper.nms;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class FakePlayerInventory {

    private static final int SIZE = 36;
    private final Map<Integer, ItemStack> items = new HashMap<>(SIZE);
    private final Player player;

    public FakePlayerInventory(final Player player) {
        this.player = player;
    }

    public void setItem(final int slot, final @Nullable ItemStack itemStack) {
        int index = slot;
        final ServerPlayer player = ((CraftPlayer) this.player).getHandle();

        if (index < Inventory.getSelectionSize()) {
            index += 36;
        } else if (index > 39) {
            index += 5;
        } else if (index > 35) {
            index = 8 - (index - 36);
        }

        items.put(slot, itemStack);

        player.connection.send(
                new ClientboundContainerSetSlotPacket(
                        player.inventoryMenu.containerId,
                        player.inventoryMenu.incrementStateId(),
                        45,
                        CraftItemStack.asNMSCopy(itemStack))
        );
        player.connection.send(
                new ClientboundContainerSetSlotPacket(
                        player.inventoryMenu.containerId,
                        player.inventoryMenu.incrementStateId(),
                        index,
                        CraftItemStack.asNMSCopy(itemStack))
        );
    }

    public @Nullable ItemStack getItem(final int slot) {
        return items.get(slot);
    }

    public void prepare() {
        /*System.out.println("SENDING!");
        for (int i = 0; i < SIZE; i++) {
            setItem(i, items.get(i));
        }*/
    }
}
