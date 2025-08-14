package com.example.plantvszombie;

import javafx.animation.Animation;

import java.util.ArrayList;

public class AnimationManager {
    private static final ArrayList<Animation> animations = new ArrayList<>();
    static boolean isPaused = false;

    public static void register(Animation a) {
        animations.add(a);
    }

    public static void pauseAll() {
        isPaused = true;
        for (Animation a : animations) {
            a.pause();
        }
    }

    public static void resumeAll() {
        isPaused = false;
        for (Animation a : animations) {
            a.play();
        }
    }
}
