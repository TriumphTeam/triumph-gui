package dev.triumphteam.gui.example.examples;

import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.builder.item.ItemBuilder;
import dev.triumphteam.gui.slot.Slot;
import dev.triumphteam.gui.state.pagination.PagerState;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Paginated implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        // Simple example to fill in the pages
        final var materials = Arrays.stream(Material.values()).filter(it -> it.isItem() && !it.isAir()).toList();

        // Create a stage with materials as elements and a box layout from 2,3 to 4,7
        // We are doing the state first because we'll share it on the title
        final PagerState<Material> pageState = PagerState.of(materials, GuiLayout.box(Slot.of(2, 3), Slot.of(4, 7)));

        Gui.of(6)
                .title(title -> {
                    title.remember(pageState);

                    title.render(() -> Component.text("Page: " + pageState.getCurrentPage()));
                })
                .component(component -> {
                    component.remember(pageState);

                    component.render(container -> {

                        // Loop through the page and set items to the container
                        pageState.forEach((entry) -> {
                            // Create a new item stack with the material provided by the pager
                            container.setItem(entry.slot(), ItemBuilder.from(entry.element()).asGuiItem());
                        });

                        // Page control buttons
                        // This can also be on a different component if you don't need them to change
                        container.setItem(Slot.of(6, 2), ItemBuilder.from(Material.PAPER).name(Component.text("Previous page"))
                                .asGuiItem((player, context) -> {


                                }));
                        container.setItem(Slot.of(6, 8), ItemBuilder.from(Material.PAPER).name(Component.text("Next page")).asGuiItem((player, context) -> pageState.next()));
                    });
                })
                .build();

        return true;
    }
}
