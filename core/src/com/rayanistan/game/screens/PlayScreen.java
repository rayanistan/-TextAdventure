package com.rayanistan.game.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.factories.PlayerFactory;
import com.rayanistan.game.factories.WizardFactory;
import com.rayanistan.game.systems.*;
import com.rayanistan.game.utils.Assets;
import com.rayanistan.game.utils.BodyUtils;
import com.rayanistan.game.utils.BoundedCamera;
import com.rayanistan.game.utils.TiledLevel;

import static com.rayanistan.game.NotTextAdventure.V_HEIGHT;
import static com.rayanistan.game.NotTextAdventure.V_WIDTH;


public class PlayScreen extends ScreenAdapter {

    private World world;
    private NotTextAdventure app;

    private ExtendViewport viewport;
    private BoundedCamera camera;

    private PooledEngine entityEngine;

    public PlayScreen(final NotTextAdventure app) {
        this.app = app;

        this.world = new World(new Vector2(0, -9.8f), true);

        camera = new BoundedCamera();

        viewport = new ExtendViewport(V_WIDTH, V_HEIGHT, camera);
        viewport.apply(true);

        entityEngine = new PooledEngine();
        BodyUtils.setWorld(world);
    }

    @Override
    public void show() {
        TiledLevel level = new TiledLevel(Assets.getMap1());

        level.parseCollisionLayer();

        camera.setBounds(0, level.getDimensionsInPixels().x, 0, level.getDimensionsInPixels().y);

        viewport.setMaxWorldWidth(level.getDimensionsInPixels().x);
        viewport.setMaxWorldHeight(level.getDimensionsInPixels().y);

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
        TiledRenderingSystem tiledRenderingSystem = new TiledRenderingSystem(level.map, camera);


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
        MapProperties playerProps = playerSpawn.getProperties();

        MapObject wizardSpawn = level.getEvents().get("WizardSpawn");
        MapProperties wizardProps = wizardSpawn.getProperties();

        PlayerFactory.spawnEntity(entityEngine, new Vector2(playerProps.get("x", Float.class),
                playerProps.get("y", Float.class)));

        WizardFactory.spawnEntity(entityEngine, new Vector2(wizardProps.get("x", Float.class),
                wizardProps.get("y", Float.class)));

        new CollisionSystem(world).collisionListeners.add(collisionSystem);

        entityEngine.addSystem(collisionSystem);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        entityEngine.update(delta);
    }
}
