package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.scenes.BasicScene;
import com.rayanistan.game.utils.GameCamera;

import static com.rayanistan.game.NotTextAdventure.*;

public class PlayState extends AbstractState {

    // Setup first scene
    private BasicScene scene1;

    // Box2D world handles physics simulation
    private World world;

    // Re-purpose camera into bounded camera to avoid scrolling offscreen
    public GameCamera cam;

    // Public ctor
    public PlayState(final NotTextAdventure app) {
        super(app);

        world = new World(new Vector2(0, -9.8f), true);

        // Initialize utility camera
        cam = new GameCamera();

        // Make extend viewport to maintain aspect ratio
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        viewport.apply();
    }

    @Override
    public void show() {
        scene1 = new BasicScene(1, this, world, app.assets.get("maps/stage1.tmx", TiledMap.class));
    }

    @Override
    public void update(float dt) {
        // Update camera
        scene1.update(dt);

        // Display frame rate
        Gdx.graphics.setTitle(TITLE + " FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void render(float dt) {
        super.render(dt);

        // Set clear color to white
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        scene1.render();
    }


    @Override
    public void dispose() {
        world.dispose();
        scene1.dispose();
    }

    public NotTextAdventure getApp() {
        return app;
    }
}
