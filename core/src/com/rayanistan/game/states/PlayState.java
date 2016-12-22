package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.handlers.GameStateManager;
import com.rayanistan.game.utils.CameraUtils;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.NotTextAdventure.DEBUG;
import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public class PlayState extends AbstractState {

    public TextureAtlas atlas;
    private Player player;

    // Box2D Stuff
    public World world;
    private Box2DDebugRenderer debugRenderer;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        cam.setToOrtho(false, width / 2, height / 2);

        atlas = game.assets.get("player.atlas");
        player = new Player(this);

        WorldUtils.createBox(world, 32, 12, 32 * 20,
                32, true, null);
    }

    @Override
    public void update(float dt) {
        // Update camera
        cameraUpdate();

        // Update world
        world.step(1 / 60f, 6, 2);

        // Update player
        player.update(dt);

    }

    private void cameraUpdate() {
        CameraUtils.lerpToTarget(cam, player.getCenter());
    }

    @Override
    public void render(float dt) {
        super.render(dt);

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        player.render(game.batch);
        game.batch.end();

        if (DEBUG)
            debugRenderer.render(world, cam.combined.cpy().scl(PPM));
    }


    @Override
    public void dispose() {
        atlas.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
