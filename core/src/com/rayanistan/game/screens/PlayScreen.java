package com.rayanistan.game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.archetypes.PlayerArchetype;
import com.rayanistan.game.interfaces.CollisionListener;
import com.rayanistan.game.systems.*;
import com.rayanistan.game.utils.BodyUtils;
import com.rayanistan.game.utils.BoundedCamera;
import com.rayanistan.game.utils.TiledLevel;

import static com.rayanistan.game.NotTextAdventure.*;


public class PlayScreen extends ScreenAdapter {

    private World world;
    private NotTextAdventure app;

    private Viewport viewport;
    private BoundedCamera camera;

    private Engine entityEngine;

    public PlayScreen(final NotTextAdventure app) {
        this.app = app;

        this.world = new World(new Vector2(0, -9.8f), true);

        camera = new BoundedCamera();

        viewport = new ExtendViewport(V_WIDTH, V_HEIGHT, camera);
        viewport.apply(true);

        entityEngine = new Engine();
        BodyUtils.setWorld(world);
    }

    @Override
    public void show() {
        // TODO: Implement an EventSystem, TiledLevelSystem
        TiledLevel level = new TiledLevel(1);
        level.initialize(new FileHandle("json/Level1.json"), app.assets);
        level.parseCollisionLayer();

        camera.setBounds(0, level.getDimensionsInPixels().x, 0, level.getDimensionsInPixels().y);

        // Configure all systems
        AnimationSystem animationSystem = new AnimationSystem();
        TransformSystem transformSystem = new TransformSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem(world);
        SpriteRenderingSystem spriteRenderingSystem = new SpriteRenderingSystem(app.batch, camera);
        DebugRenderingSystem debugRenderingSystem = new DebugRenderingSystem(world, camera);
        PlayerInputSystem playerInputSystem = new PlayerInputSystem();
        CameraSystem cameraSystem = new CameraSystem(camera);
        PlayerStateSystem stateSystem = new PlayerStateSystem();
        PlayerCollisionSystem collisionSystem = new PlayerCollisionSystem();
        TiledRenderingSystem tiledRenderingSystem = new TiledRenderingSystem(level.tileMap, camera);

        entityEngine.addSystem(playerInputSystem);
        entityEngine.addSystem(stateSystem);
        entityEngine.addSystem(animationSystem);
        entityEngine.addSystem(cameraSystem);
        entityEngine.addSystem(transformSystem);
        entityEngine.addSystem(tiledRenderingSystem);
        entityEngine.addSystem(spriteRenderingSystem);
        entityEngine.addSystem(physicsSystem);
        entityEngine.addSystem(debugRenderingSystem);

        MapObject playerSpawn = level.getEvents().get("PlayerSpawn");
        MapProperties props = playerSpawn.getProperties();

        entityEngine.addEntity(PlayerArchetype.spawnEntity(app.assets,
                new Vector2(props.get("x", Float.class), props.get("y", Float.class))));

        new CollisionSystem(world, new Array<CollisionListener>()).collisionListeners.add(collisionSystem);

        entityEngine.addSystem(collisionSystem);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        entityEngine.update(delta);
    }
}
