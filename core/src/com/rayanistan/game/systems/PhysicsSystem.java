package com.rayanistan.game.systems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.physics.box2d.World;

import static com.rayanistan.game.utils.BodyUtils.POSITION_ITERATIONS;
import static com.rayanistan.game.utils.BodyUtils.STEP;
import static com.rayanistan.game.utils.BodyUtils.VELOCITY_ITERATIONS;

public class PhysicsSystem extends IntervalSystem {

    private World world;

    public PhysicsSystem(World world) {
        super(STEP);
        this.world = world;
    }

    @Override
    protected void updateInterval() {
        world.step(STEP, POSITION_ITERATIONS, VELOCITY_ITERATIONS);
    }
}
