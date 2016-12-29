package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rayanistan.game.utils.GameCamera;

public class DebugRenderSystem extends IteratingSystem {

    private World world;
    private GameCamera camera;
    private Box2DDebugRenderer debugRenderer;

    public DebugRenderSystem(World world, GameCamera camera) {
        super(Family.all().get());

        this.world = world;
        this.camera = camera;
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
