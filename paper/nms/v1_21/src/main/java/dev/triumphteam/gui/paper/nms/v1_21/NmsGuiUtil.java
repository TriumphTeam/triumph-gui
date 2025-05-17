package dev.triumphteam.gui.paper.nms.v1_21;

import com.mojang.datafixers.util.Pair;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.event.inventory.InventoryCloseEvent.Reason.OPEN_NEW;

final class NmsGuiUtil {

    /**
     * This method follows the CraftHumanEntity#openCustomInventory method.
     *
     * @param player      The player to open the inventory for.
     * @param container   The container to open.
     * @param title       The title of the inventory.
     * @param type        The type of the inventory.
     * @param containerId The container id to use.
     */
    public static void openInventory(
            final @NotNull ServerPlayer player,
            final @NotNull AbstractContainerMenu container,
            final @NotNull Component title,
            final @NotNull MenuType<?> type,
            final int containerId
    ) {
        if (player.containerMenu != player.inventoryMenu) {
            // fire INVENTORY_CLOSE if one already open
            player.connection.handleContainerClose(
                    new ServerboundContainerClosePacket(player.containerMenu.containerId),
                    OPEN_NEW
            ); // Paper - Inventory close reason
        }

        // Call inventory open event.
        final Pair<Component, AbstractContainerMenu> result = CraftEventFactory.callInventoryOpenEventWithTitle(player, container);
        // Event likely canceled.
        if (result.getSecond() == null) return;

        Component inventoryTitle = title;

        // Overridable by the event.
        final Component titleOverride = result.getFirst();
        if (titleOverride != null) {
            inventoryTitle = titleOverride;
        }

        if (!player.isImmobile()) {
            player.connection.send(new ClientboundOpenScreenPacket(containerId, type, PaperAdventure.asVanilla(inventoryTitle)));
        }

        player.containerMenu = container;
        player.initMenu(container);
    }
}
