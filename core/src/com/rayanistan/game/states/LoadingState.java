package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.rayanistan.game.handlers.GameStateManager;

public class LoadingState extends AbstractState {

    private float progress;
    private ShapeRenderer shapeRenderer;

    public LoadingState(final GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();

        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    @Override
    public void show() {

        this.progress = 0f;

        queueAssets();
    }

    private void queueAssets() {
        app.assets.load("atlas.atlas", TextureAtlas.class);
    }

    @Override
    public void update(float dt) {
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .1f);

        if (app.assets.update() && progress >= app.assets.getProgress() - .00001f) {
            gsm.setState(GameStateManager.State.PLAY);
        }
    }

    @Override
    public void render(float dt) {

        super.render(dt);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, cam.viewportWidth - 64, 16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, progress * (cam.viewportWidth - 64), 16);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
