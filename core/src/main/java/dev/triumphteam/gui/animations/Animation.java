package dev.triumphteam.gui.animations;

import dev.triumphteam.gui.animations.impl.CustomAnimation;
import dev.triumphteam.gui.animations.impl.InfiniteAnimation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public interface Animation {

    /**
     * Reset to the initial frame
     */
    void reset();

    /**
     * Go to the next frame
     * @return the next frame or null if it is the last frame
     */
    @Nullable Frame nextFrame();

    /**
     * Start the animation
     */
    void start();

    /**
     * @return the delay between frames
     */
    int getDelay();

    /**
     * @return the bukkit runnable that handles frame updates
     */
    AnimationRunner getRunnable();

    static @NotNull Animation of(int delay, Frame... frames) {
        return new CustomAnimation(delay, Arrays.asList(frames));
    }

    static @NotNull Animation infinite(int delay, Frame... frames) {
        return new InfiniteAnimation(delay, Arrays.asList(frames));
    }
}
