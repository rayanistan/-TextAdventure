package com.rayanistan.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public class DebugRenderingSystem extends EntitySystem {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public DebugRenderingSystem(World world, OrthographicCamera camera) {
        this.world = world;
        debugRenderer = new Box2DDebugRenderer();
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        debugRenderer.render(world, camera.combined.cpy().scl(PPM));
    }
}
