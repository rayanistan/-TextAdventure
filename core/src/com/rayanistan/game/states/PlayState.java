package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.entities.NPC;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.entities.Wizard;
import com.rayanistan.game.utils.CameraUtils;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.NotTextAdventure.*;
import static com.rayanistan.game.utils.WorldUtils.Constants.*;

public class PlayState extends AbstractState {

    // Declare player class that holds a box2d body with a sprite to render over it
    private Player player;
    private Array<NPC> npcs;

    // Box2D world handles physics simulation
    private World world;

    // Debug renderer handle rendering debug lines if DEBUG
    private Box2DDebugRenderer debugRenderer;

    // Public ctor
    public PlayState(final NotTextAdventure app) {
        super(app);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

        npcs = new Array<NPC>();
    }

    @Override
    public void show() {
        player = new Player(world, (TextureAtlas) app.assets.get("sprites/player.atlas"));

        npcs.add(new Wizard(world, (TextureAtlas) app.assets.get("sprites/npc.atlas")));

        WorldUtils.createBox(world, 32, 12, 32 * 20,
                32, true, null, GROUND_BITS,
                (short) (WIZARD_BITS | PLAYER_BITS));
    }

    @Override
    public void update(float dt) {
        // Update camera
        cameraUpdate();

        // Update physics simulation
        world.step(STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        // Update player's sprite
        player.update(dt);

        // Update all of the NPC's sprites
        for (NPC npc : npcs) {
            npc.update(dt);
        }

        // Display frame rate
        Gdx.graphics.setTitle(TITLE + " FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    private void cameraUpdate() {
        CameraUtils.lerpToTarget(cam, player.getCenter());
    }

    @Override
    public void render(float dt) {
        super.render(dt);

        // Set clear color to white
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        // Set sprite batch's projection matrix4 to the camera.projection * camera.view
        app.batch.setProjectionMatrix(cam.combined);

        // Begin sprite batch operations
        app.batch.begin();

        // Render player
        player.render(app.batch);

        // Update all of the NPC's sprites
        for (NPC npc : npcs) {
            npc.render(app.batch);
        }

        // Flush sprite batch to GPU
        app.batch.end();

        // If DEBUG => render box2d debug lines up-scaled by Pixel Per Meter ratio
        if (DEBUG)
            debugRenderer.render(world, cam.combined.cpy().scl(PPM));
    }


    @Override
    public void dispose() {
        player.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
