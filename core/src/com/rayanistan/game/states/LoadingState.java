package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.rayanistan.game.handlers.GameStateManager;

public class LoadingState extends AbstractState {

    private float progress;

    public LoadingState(final GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        game.shapeRenderer.setProjectionMatrix(cam.combined);
    }

    @Override
    public void show() {

        this.progress = 0f;

        queueAssets();
    }

    private void queueAssets() {
        game.assets.load("atlas.pack", TextureAtlas.class);
    }

    @Override
    public void update(float dt) {
        progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f);

        if (game.assets.update() && progress >= game.assets.getProgress() - .00001f) {
            gsm.setState(GameStateManager.State.PLAY);
        }
    }

    @Override
    public void render(float dt) {

        super.render(dt);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, cam.viewportWidth - 64, 16);

        game.shapeRenderer.setColor(Color.BLUE);
        game.shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, progress * (cam.viewportWidth - 64), 16);
        game.shapeRenderer.end();
    }

    @Override
    public void dispose() {}
}
