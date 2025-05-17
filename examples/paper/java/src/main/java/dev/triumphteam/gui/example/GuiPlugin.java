/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
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
package dev.triumphteam.gui.example;

import com.github.retrooper.packetevents.PacketEvents;
import dev.triumphteam.gui.example.examples.Anvil;
import dev.triumphteam.gui.example.examples.CookieClicker;
import dev.triumphteam.gui.example.examples.Static;
import dev.triumphteam.gui.example.examples.UpdatingTitle;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuiPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        //On Bukkit, calling this here is essential, hence the name "load"
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        //Initialize!
        PacketEvents.getAPI().init();

        getCommand("gui-static").setExecutor(new Static());
        getCommand("gui-clicker").setExecutor(new CookieClicker());
        getCommand("gui-title").setExecutor(new UpdatingTitle());
        getCommand("gui-anvil").setExecutor(new Anvil());
    }
}
