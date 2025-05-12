package dev.triumphteam.gui.paper.nms;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import org.bukkit.entity.Player;

public final class PacketEventsPacketListener implements PacketListener {

    public static void uhhh() {
        /* We will register our packet listeners in the onLoad method */
        PacketEvents.getAPI().getEventManager().registerListener(
                new PacketEventsPacketListener(), PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        // The user represents the player.
        User user = event.getUser();
        final var player = event.<Player>getPlayer();

        // Identify what kind of packet it is.
        if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
            final var wrapper = new WrapperPlayServerSetSlot(event);
            // System.out.println("Sending set slot -> " + wrapper.getSlot() + ", state -> " + wrapper.getStateId());
        }

        if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
            System.out.println(event.getPacketId());
            final var wrapper = new WrapperPlayServerWindowItems(event);
            System.out.println("Items: " + wrapper.getItems());
        }

        if (event.getPacketType() == PacketType.Play.Server.COLLECT_ITEM) {
            player.sendMessage("Collect items??");
        }
    }
}
