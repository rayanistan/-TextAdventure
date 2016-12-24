package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.rayanistan.game.NotTextAdventure;

import static com.rayanistan.game.NotTextAdventure.V_HEIGHT;
import static com.rayanistan.game.NotTextAdventure.V_WIDTH;

public class LoadingState extends AbstractState {

    // Float that keeps the progress of the amount of assets loaded
    private float progress;

    // Public ctor
    public LoadingState(final NotTextAdventure app) {
        super(app);

        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

    }

    @Override
    public void show() {
        app.shapeRenderer.setProjectionMatrix(cam.combined);

        this.progress = 0f;

        // Begins to load assets used in the app
        queueAssets();
    }

    private void queueAssets() {
        app.assets.load("sprites/player.atlas", TextureAtlas.class);
    }

    @Override
    public void update(float dt) {
        // Use linear interpolation to get a smooth loading bar
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .1f);

        // If finished updating & progress bar is complete, then change state to PLAY
        if (app.assets.update() && progress >= app.assets.getProgress() - .00001f) {
            app.setScreen(app.playState);
        }
    }

    @Override
    public void render(float dt) {

        super.render(dt);

        // Draw loading bar
        app.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        app.shapeRenderer.setColor(Color.BLACK);
        app.shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, cam.viewportWidth - 64, 16);

        app.shapeRenderer.setColor(Color.BLUE);
        app.shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, progress * (cam.viewportWidth - 64), 16);
        app.shapeRenderer.end();
    }

    @Override
    public void dispose() {}
}
