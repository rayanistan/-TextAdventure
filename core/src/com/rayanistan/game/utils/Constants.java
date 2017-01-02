package com.rayanistan.game.utils;

public final class Constants {

    // Pixel per meter ratio, used to convert Box2D units over to screen units
    public final static int PPM = 32;

    // World step, How many times should the physics world update
    public final static float STEP = 1 / 60f;

    // Velocity Iterations and Position Iterations, don't know too much about
    public final static int VELOCITY_ITERATIONS = 6;
    public final static int POSITION_ITERATIONS = 2;
}
