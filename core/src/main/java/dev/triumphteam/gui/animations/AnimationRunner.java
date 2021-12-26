package dev.triumphteam.gui.animations;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class AnimationRunner extends BukkitRunnable {
    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(AnimationRunner.class);
    private final Animation animation;

    public AnimationRunner(@NotNull Animation animation) {
        this.animation = animation;

        this.runTaskTimerAsynchronously(plugin,0,animation.getDelay());
    }

    @Override
    public void run() {
        Frame frame = animation.nextFrame();
        if (frame == null) {
            this.cancel();
        }
    }

}
