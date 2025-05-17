package dev.triumphteam.gui.paper.nms.v1_21;

import com.mojang.datafixers.util.Pair;
import dev.triumphteam.gui.paper.nms.v1_21.inventory.NmsGuiReflectionHandler;
import dev.triumphteam.gui.paper.nms.v1_21.inventory.PaperGuiInventory;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.view.CraftAnvilView;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.event.inventory.InventoryCloseEvent.Reason.OPEN_NEW;

public final class NmsAnvilInventory extends AnvilMenu implements PaperGuiInventory {

    private static final NmsGuiReflectionHandler REFLECTION_HANDLER = new NmsGuiReflectionHandler(SimpleContainer.class);
    private static final int RESULT_SLOT = 2;

    private final Component title;
    private final AnvilInventory bukkitInventory;
    private final CraftAnvilView bukkitEntity;

    private final ServerPlayer player;
    private final Inventory playerInventory;

    public NmsAnvilInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final boolean usePlayerInventory
    ) {
        this(holder, ((CraftPlayer) player).getHandle(), title, usePlayerInventory);
    }

    private NmsAnvilInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull ServerPlayer player,
            final @NotNull Component title,
            final boolean usePlayerInventory
    ) {
        this(holder, player, title, usePlayerInventory ? new Inventory(player) : player.getInventory());
    }

    private NmsAnvilInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull ServerPlayer player,
            final @NotNull Component title,
            final @NotNull Inventory playerInventory
    ) {
        super(player.nextContainerCounter(), playerInventory, ContainerLevelAccess.create(player.level(), player.blockPosition()));

        this.title = title;
        this.player = player;

        this.playerInventory = playerInventory;

        // Set up bukkit things.
        this.bukkitInventory = new CraftInventoryAnvil(access.getLocation(), inputSlots, resultSlots);
        this.bukkitEntity = new CraftAnvilView(player.getBukkitEntity(), bukkitInventory, this);
        // Little hack to allow us to set an inventory holder.
        REFLECTION_HANDLER.setBukkitOwner(inputSlots, holder);
    }

    @Override
    public void open() {
        if (player.containerMenu != player.inventoryMenu) {
            // fire INVENTORY_CLOSE if one already open
            player.connection.handleContainerClose(
                    new ServerboundContainerClosePacket(player.containerMenu.containerId),
                    OPEN_NEW
            ); // Paper - Inventory close reason
        }

        // This method follows the CraftHumanEntity#openCustomInventory method.
        // Call inventory open event.
        final Pair<Component, AbstractContainerMenu> result = CraftEventFactory.callInventoryOpenEventWithTitle(player, this);
        // Event likely canceled.
        if (result.getSecond() == null) return;

        Component title = this.title;

        // Overridable by the event.
        final Component titleOverride = result.getFirst();
        if (titleOverride != null) {
            title = titleOverride;
        }

        if (!player.isImmobile()) {
            player.connection.send(new ClientboundOpenScreenPacket(containerId, MenuType.ANVIL, PaperAdventure.asVanilla(title)));
        }

        player.containerMenu = this;
        player.initMenu(this);
    }

    @Override
    public void setTopInventoryItem(final int slot, final @NotNull org.bukkit.inventory.ItemStack itemStack) {
        if (slot < inputSlots.getContainerSize()) {
            inputSlots.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
            return;
        }

        resultSlots.setItem(RESULT_SLOT, CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void setPlayerInventoryItem(final int slot, final org.bukkit.inventory.@NotNull ItemStack itemStack) {
        playerInventory.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void clearTopInventorySlot(final int slot) {
        if (slot < inputSlots.getContainerSize()) {
            inputSlots.setItem(slot, ItemStack.EMPTY);
            return;
        }

        resultSlots.setItem(RESULT_SLOT, ItemStack.EMPTY);
    }

    @Override
    public void clearPlayerInventorySlot(final int slot) {
        playerInventory.setItem(slot, ItemStack.EMPTY);
    }

    @Override
    public @NotNull org.bukkit.inventory.Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    @Override
    public @NotNull CraftAnvilView getBukkitView() {
        return bukkitEntity;
    }

    @Override
    public void restorePlayerInventory() {
        player.inventoryMenu.sendAllDataToRemote();
    }

    @Override
    public boolean stillValid(final @NotNull Player player) {
        return true;
    }

    @Override
    protected void clearContainer(final @NotNull Player player, final @NotNull Container container) {
        // Empty, prevent items from being dropped or taken.
    }
}
