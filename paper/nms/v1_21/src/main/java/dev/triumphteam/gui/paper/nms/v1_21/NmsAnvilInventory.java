package dev.triumphteam.gui.paper.nms.v1_21;

import dev.triumphteam.gui.paper.NmsGuiReflectionHandler;
import dev.triumphteam.gui.paper.PaperGuiInventory;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.view.CraftAnvilView;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

final class NmsAnvilInventory extends AnvilMenu implements PaperGuiInventory {

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
        this(holder, player, title, usePlayerInventory ? new Inventory(player, new EntityEquipment()) : player.getInventory());
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
        maximumRepairCost = 0;
    }

    @Override
    public void open() {
        NmsGuiUtil.openInventory(player, this, title, getType(), containerId);
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
        // Always true so the player can open it from anywhere.
        return true;
    }

    @Override
    protected void clearContainer(final @NotNull Player player, final @NotNull Container container) {
        // Empty, prevent items from being dropped or taken.
    }
}
