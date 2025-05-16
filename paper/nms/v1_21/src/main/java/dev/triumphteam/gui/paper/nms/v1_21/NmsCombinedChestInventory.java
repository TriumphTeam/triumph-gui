package dev.triumphteam.gui.paper.nms.v1_21;

import com.mojang.datafixers.util.Pair;
import dev.triumphteam.gui.paper.nms.v1_21.inventory.CommonNmsCombinedChestInventory;
import dev.triumphteam.gui.paper.nms.v1_21.inventory.PaperGuiInventory;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NmsCombinedChestInventory extends AbstractContainerMenu implements PaperGuiInventory {

    private static final List<MenuType<?>> TYPES = List.of(
            MenuType.GENERIC_9x1,
            MenuType.GENERIC_9x2,
            MenuType.GENERIC_9x3,
            MenuType.GENERIC_9x4,
            MenuType.GENERIC_9x5,
            MenuType.GENERIC_9x6
    );

    private final CommonNmsCombinedChestInventory delegate;

    private final Component title;
    private final int containerRows;
    private final Inventory vanillaInventory;
    private final CraftInventoryView<?, ?> bukkitEntity;
    private final Container container;
    private final ServerPlayer player;

    public NmsCombinedChestInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final int rows
    ) {
        this(holder, ((CraftPlayer) player).getHandle(), title, rows);
    }

    private NmsCombinedChestInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull ServerPlayer player,
            final @NotNull Component title,
            final int rows
    ) {
        super(TYPES.get(rows - 1), player.nextContainerCounter());

        this.delegate = new CommonNmsCombinedChestInventory(rows);

        this.containerRows = rows;
        this.player = player;
        this.title = title;

        // Set up the full container and bukkit inventory-related things.
        this.container = new SimpleContainer(delegate.fullContainerSize());
        this.vanillaInventory = new CraftInventoryCustom(holder, delegate.topContainerSize());
        this.bukkitEntity = new CraftInventoryView<>(player.getBukkitEntity(), vanillaInventory, this);

        // Add all the container slots.
        delegate.generateSlots((slot, x, y) -> addSlot(new Slot(container, slot, x, y)));
    }

    @Override
    public void open() {
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
            player.connection.send(new ClientboundOpenScreenPacket(containerId, getType(), PaperAdventure.asVanilla(title)));
        }

        player.containerMenu = this;
        player.initMenu(this);
    }

    @Override
    public void setItem(final int slot, final @NotNull org.bukkit.inventory.ItemStack itemStack) {
        container.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void clearSlot(final int slot) {
        container.setItem(slot, ItemStack.EMPTY);
    }

    @Override
    public @NotNull Inventory getBukkitInventory() {
        return vanillaInventory;
    }

    @Override
    public @NotNull InventoryView getBukkitView() {
        return bukkitEntity;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(final @NotNull Player player, final int index) {
        // TODO: Abstract this somehow?
        final ItemStack itemStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);

        if (!slot.hasItem()) return itemStack;

        final ItemStack item = slot.getItem();

        if (index < delegate.topContainerSize()) {
            if (!this.moveItemStackTo(item, this.containerRows * 9, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(item, 0, this.containerRows * 9, false)) {
            return ItemStack.EMPTY;
        }

        if (item.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return item.copy();
    }

    @Override
    public void removed(final @NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean stillValid(final @NotNull Player player) {
        if (!this.checkReachable) return true;
        return this.container.stillValid(player);
    }

    @Override
    public void restorePlayerInventory() {
        player.inventoryMenu.sendAllDataToRemote();
    }
}
