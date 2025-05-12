package dev.triumphteam.gui.paper.nms;

public final class GivingItATry {

    /*private final Container container = new SimpleContainer(9 * 6);
    private final ServerPlayer player;
    private org.bukkit.craftbukkit.inventory.CraftInventoryView bukkitEntity = null;

    public GivingItATry(final ServerPlayer player) {
        super(MenuType.GENERIC_9x6, player.nextContainerCounter());
        this.player = player;
    }

    public void open() {
        // call the InventoryOpenEvent
        CraftEventFactory.callInventoryOpenEvent(player, this);

        // set active container
        player.containerMenu = this;

        // send an open packet
        player.connection.send(new ClientboundOpenScreenPacket(containerId, MenuType.GENERIC_9x6, Component.literal("Hiiii")));

        // send initial items
        NonNullList<ItemStack> itemsList = NonNullList.withSize(90, ItemStack.EMPTY);
        player.connection.send(new ClientboundContainerSetContentPacket(
                player.containerMenu.containerId,
                incrementStateId(),
                itemsList,
                ItemStack.EMPTY
        ));

        // init menu
        player.initMenu(this);
    }

    @Override
    public org.bukkit.craftbukkit.inventory.CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }

        org.bukkit.craftbukkit.inventory.CraftInventory inventory;
        if (this.container instanceof Inventory) {
            inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryPlayer((Inventory) this.container);
        } else if (this.container instanceof net.minecraft.world.CompoundContainer) {
            inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest((net.minecraft.world.CompoundContainer) this.container);
        } else {
            inventory = new org.bukkit.craftbukkit.inventory.CraftInventory(this.container);
        }

        this.bukkitEntity = new org.bukkit.craftbukkit.inventory.CraftInventoryView(this.player.getBukkitEntity(), inventory, this);
        return this.bukkitEntity;
    }

    @Override
    public boolean isValidSlotIndex(final int slotIndex) {
        return true;
    }

    @Override
    public Slot getSlot(final int slotId) {
        return new Slot(this.container, slotId, 0, 0);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack item = slot.getItem();
            itemStack = item.copy();
            if (index < 6 * 9) {
                if (!this.moveItemStackTo(item, 6 * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(item, 0, 6 * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (item.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        if (!this.checkReachable) return true; // CraftBukkit
        return this.container.stillValid(player);
    }*/
}
