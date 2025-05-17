package dev.triumphteam.gui.paper.nms.v1_21;

import dev.triumphteam.gui.paper.PaperGuiInventory;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.triumphteam.gui.container.type.GuiContainerType.COLUMNS;

final class NmsCombinedChestInventory extends ChestMenu implements PaperGuiInventory {

    private static final List<MenuType<?>> TYPES = List.of(
            MenuType.GENERIC_9x1,
            MenuType.GENERIC_9x2,
            MenuType.GENERIC_9x3,
            MenuType.GENERIC_9x4,
            MenuType.GENERIC_9x5,
            MenuType.GENERIC_9x6
    );

    private final Component title;
    private final org.bukkit.inventory.Inventory bukkitInventory;
    private final CraftInventoryView<?, ?> bukkitEntity;

    private final ServerPlayer player;

    private final Container container;
    private final Inventory playerInventory;

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
        this(holder, player, title, new Inventory(player, new EntityEquipment()), new SimpleContainer(rows * COLUMNS), rows);
    }

    private NmsCombinedChestInventory(
            final @NotNull InventoryHolder holder,
            final @NotNull ServerPlayer player,
            final @NotNull Component title,
            final @NotNull net.minecraft.world.entity.player.Inventory playerInventory,
            final @NotNull Container container,
            final int rows
    ) {
        super(TYPES.get(rows - 1), player.nextContainerCounter(), playerInventory, container, rows);

        this.title = title;
        this.player = player;

        this.playerInventory = playerInventory;
        this.container = container;

        // Set up bukkit things.
        this.bukkitInventory = new CraftInventoryCustom(holder, container.getContainerSize());
        this.bukkitEntity = new CraftInventoryView<>(player.getBukkitEntity(), bukkitInventory, this);
    }

    @Override
    public void open() {
        NmsGuiUtil.openInventory(player, this, title, getType(), containerId);
    }

    @Override
    public void setTopInventoryItem(final int slot, final @NotNull org.bukkit.inventory.ItemStack itemStack) {
        container.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void setPlayerInventoryItem(final int slot, final org.bukkit.inventory.@NotNull ItemStack itemStack) {
        playerInventory.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void clearTopInventorySlot(final int slot) {
        getContainer().setItem(slot, ItemStack.EMPTY);
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
    public @NotNull CraftInventoryView<?, ?> getBukkitView() {
        return bukkitEntity;
    }

    @Override
    public void restorePlayerInventory() {
        player.inventoryMenu.sendAllDataToRemote();
    }
}
