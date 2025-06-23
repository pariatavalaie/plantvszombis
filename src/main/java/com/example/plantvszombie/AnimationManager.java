package com.example.plantvszombie;

import javafx.animation.Animation;
import java.util.ArrayList;

public class AnimationManager {
    private static final ArrayList<Animation> animations = new ArrayList<>();

    public static void register(Animation a) {
        animations.add(a);
    }

    public static void pauseAll() {
        for (Animation a : animations) {
            a.pause();
        }
    }

    public static void resumeAll() {
        for (Animation a : animations) {
            a.play();
        }
    }

    public static void stopAndClearAll() {
        for (Animation a : animations) {
            a.stop();
        }
        animations.clear();
    }
}
