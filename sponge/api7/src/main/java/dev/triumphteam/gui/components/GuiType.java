/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.components;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryCapacity;
import org.spongepowered.common.registry.type.item.InventoryArchetypeRegistryModule;

// TODO COMMENTS
public enum GuiType {

    CHEST(Archetypes.GUI_CHEST, 9),
    WORKBENCH(Archetypes.GUI_WORKBENCH, 9),
    HOPPER(Archetypes.GUI_HOPPER, 5),
    DISPENSER(Archetypes.GUI_DISPENSER, 8),
    BREWING(Archetypes.GUI_BREWING, 4);

    @NotNull
    private final InventoryArchetype inventoryType;
    private final int limit;

    GuiType(@NotNull final InventoryArchetype inventoryType, final int limit) {
        this.inventoryType = inventoryType;
        this.limit = limit;
    }

    @NotNull
    public InventoryArchetype getInventoryType() {
        return inventoryType;
    }

    public int getLimit() {
        return this.limit;
    }

    public static class Archetypes {

        public static final InventoryArchetype GUI_CHEST;
        public static final InventoryArchetype GUI_WORKBENCH;
        public static final InventoryArchetype GUI_HOPPER;
        public static final InventoryArchetype GUI_DISPENSER;
        public static final InventoryArchetype GUI_BREWING;

        static {
            GUI_CHEST = InventoryArchetype.builder().from(InventoryArchetypes.CHEST).property(InventoryCapacity.of(9)).build("triumph:chest", "chest");
            GUI_WORKBENCH = InventoryArchetype.builder().from(InventoryArchetypes.WORKBENCH).property(InventoryCapacity.of(9)).build("triumph:workbench", "workbench");
            GUI_HOPPER = InventoryArchetype.builder().from(InventoryArchetypes.HOPPER).property(InventoryCapacity.of(5)).build("triumph:hopper", "hopper");
            GUI_DISPENSER = InventoryArchetype.builder().from(InventoryArchetypes.DISPENSER).property(InventoryCapacity.of(8)).build("triumph:dispenser", "dispenser");
            GUI_BREWING = InventoryArchetype.builder().from(InventoryArchetypes.BREWING_STAND).property(InventoryCapacity.of(4)).build("triumph:brewing", "brewing");
            InventoryArchetypeRegistryModule.getInstance().registerAdditionalCatalog(GUI_CHEST);
            InventoryArchetypeRegistryModule.getInstance().registerAdditionalCatalog(GUI_WORKBENCH);
            InventoryArchetypeRegistryModule.getInstance().registerAdditionalCatalog(GUI_HOPPER);
            InventoryArchetypeRegistryModule.getInstance().registerAdditionalCatalog(GUI_DISPENSER);
            InventoryArchetypeRegistryModule.getInstance().registerAdditionalCatalog(GUI_BREWING);
        }
    }
}
