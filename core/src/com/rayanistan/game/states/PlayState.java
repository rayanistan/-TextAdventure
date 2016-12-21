package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.handlers.GameStateManager;
import com.rayanistan.game.utils.CameraUtils;

public class PlayState extends AbstractState {

    private TextureAtlas atlas;
    private Player player;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        cam.setToOrtho(false, width / 2, height / 2);

        atlas = app.assets.get("atlas.atlas");
        player = new Player(atlas);
    }

    @Override
    public void update(float dt) {
        // Update camera
        cameraUpdate();

        // Update player
        player.update(dt);
    }

    private void cameraUpdate() {
        Vector2 target = new Vector2(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
        CameraUtils.lerpToTarget(cam, target);
    }

    @Override
    public void render(float dt) {
        super.render(dt);

        app.batch.setProjectionMatrix(cam.combined);

        app.batch.begin();
        player.draw(app.batch);
        app.batch.end();
    }


    @Override
    public void dispose() {
        atlas.dispose();
    }
}
