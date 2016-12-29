package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.components.PhysicsComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.Mappers;

import static com.rayanistan.game.utils.WorldUtils.Constants.POSITION_ITERATIONS;
import static com.rayanistan.game.utils.WorldUtils.Constants.STEP;
import static com.rayanistan.game.utils.WorldUtils.Constants.VELOCITY_ITERATIONS;

public class PhysicsSystem extends IteratingSystem {

    private World world;
    private Array<Entity> physicsQueue;

    public PhysicsSystem(World world) {
        super(Family.all(PhysicsComponent.class,
                TransformComponent.class).get());

        this.world = world;
        physicsQueue = new Array<Entity>();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        world.step(STEP, POSITION_ITERATIONS, VELOCITY_ITERATIONS);

        for (Entity e : physicsQueue) {
            TransformComponent tc = Mappers.transformMapper.get(e);
            PhysicsComponent pc = Mappers.physicsMapper.get(e);

            Vector2 position = pc.body.getPosition();
            tc.pos.x = position.x;
            tc.pos.y = position.y;
            tc.rotation = pc.body.getAngle() * MathUtils.radiansToDegrees;
        }

        physicsQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        physicsQueue.add(entity);
    }
}
