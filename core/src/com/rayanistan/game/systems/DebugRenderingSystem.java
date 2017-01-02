package com.rayanistan.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rayanistan.game.utils.BoundedCamera;

import static com.rayanistan.game.utils.Constants.PPM;

public class DebugRenderingSystem extends EntitySystem {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private BoundedCamera camera;

    public DebugRenderingSystem(World world, BoundedCamera camera) {
        this.world = world;
        debugRenderer = new Box2DDebugRenderer();
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        debugRenderer.render(world, camera.getDebugMatrix(PPM));
    }
}
