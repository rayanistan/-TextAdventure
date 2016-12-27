package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.entities.NPC;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.utils.GameCamera;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.NotTextAdventure.*;
import static com.rayanistan.game.utils.WorldUtils.Constants.*;

public class PlayState extends AbstractState {

    // Declare player class that holds a box2d body with a sprite to render over it
    private Player player;
    private Array<NPC> npcs;

    // Box2D world handles physics simulation
    private World world;

    // Re-purpose camera into bounded camera to avoid scrolling offscreen
    private GameCamera cam;

    // Debug renderer handle rendering debug lines if DEBUG
    private Box2DDebugRenderer debugRenderer;

    // Stuff for tiled
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer renderer;

    // Public ctor
    public PlayState(final NotTextAdventure app) {
        super(app);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Initialize utility camera
        cam = new GameCamera();

        // Make extend viewport to maintain aspect ratio
        viewport = new ExtendViewport(V_WIDTH, V_HEIGHT, cam);
        viewport.apply();

        npcs = new Array<NPC>();
    }

    @Override
    public void show() {

        tileMap = app.assets.get("maps/stage1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tileMap);

        int levelWidth = tileMap.getProperties().get("width", Integer.class);
        int levelHeight = tileMap.getProperties().get("height", Integer.class);
        int tileWidth = tileMap.getProperties().get("tilewidth", Integer.class);
        int tileHeight = tileMap.getProperties().get("tileheight", Integer.class);

        this.cam.setBounds(0, levelWidth * tileWidth, 0, levelHeight * tileHeight);

        player = WorldUtils.createPlayer(world, app.assets.get("sprites/player.atlas",
                TextureAtlas.class), tileMap.getLayers().get("entities"));

        WorldUtils.createEntities(world, app.assets.get("sprites/npc.atlas", TextureAtlas.class),
                tileMap.getLayers().get("entities"), npcs, GROUND_BITS);

        WorldUtils.createCollisions(world, tileMap.getLayers().get("box2d"), (short) (WIZARD_BITS | PLAYER_BITS));


    }

    @Override
    public void update(float dt) {
        // Update camera
        cam.lerpToTarget(player.getCenter().x, player.getCenter().y);

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

    @Override
    public void render(float dt) {
        super.render(dt);

        // Set clear color to white
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);


        // Begin sprite batch operations
        if (RENDER) {
            // Set sprite batch's projection matrix4 to the camera.projection [matrix4] * camera.view [matrix4]
            app.batch.setProjectionMatrix(cam.combined);

            renderer.setView(cam);
            renderer.render();

            app.batch.begin();

            // RENDER ORDER:
            // 0. TILEMAP
            // 1. PLAYER
            // 2. ALL NPCS
            // 3. ALL ENEMIES

            // Render player
            player.render(app.batch);

            // Render all of the NPC's sprites
            for (NPC npc : npcs) {
                npc.render(app.batch);
            }

            // Flush sprite batch to GPU
            app.batch.end();
        }


        // If DEBUG => render box2d debug lines up-scaled by Pixel Per Meter ratio
        if (DEBUG)
            debugRenderer.render(world, cam.combined.cpy().scl(PPM));
    }


    @Override
    public void dispose() {
        tileMap.dispose();
        renderer.dispose();
        player.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
