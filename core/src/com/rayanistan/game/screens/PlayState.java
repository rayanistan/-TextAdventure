package com.rayanistan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.scenes.BasicScene;
import com.rayanistan.game.utils.GameCamera;

import static com.rayanistan.game.NotTextAdventure.V_HEIGHT;
import static com.rayanistan.game.NotTextAdventure.V_WIDTH;

public class PlayState extends ScreenAdapter {

    // Setup first scene
    private BasicScene scene1;

    // Box2D world handles physics simulation
    private World world;
    public final NotTextAdventure app;

    // Re-purpose camera into bounded camera to avoid scrolling offscreen
    public GameCamera cam;
    private Viewport viewport;

    // Public ctor
    public PlayState(final NotTextAdventure app) {
        this.app = app;

        world = new World(new Vector2(0, -9.8f), true);

        // Initialize utility camera
        cam = new GameCamera(V_WIDTH, V_HEIGHT);

        // Make extend viewport to maintain aspect ratio
        viewport = new ExtendViewport(V_WIDTH, V_HEIGHT, cam);
        viewport.apply(true);
    }

    @Override
    public void show() {
        scene1 = new BasicScene(1, this, world, app.assets.get("maps/stage1.tmx", TiledMap.class));
    }

    private void update(float dt) {
        // Update camera
        scene1.update(dt);

        // Display frame rate
        Gdx.graphics.setTitle(NotTextAdventure.TITLE + " FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void render(float dt) {
        update(dt);

        // Set clear color to white
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scene1.render();
    }


    @Override
    public void dispose() {
        world.dispose();
        scene1.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public NotTextAdventure getApp() {
        return app;
    }
}
