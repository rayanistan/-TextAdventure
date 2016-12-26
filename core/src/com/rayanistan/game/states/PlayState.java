package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.entities.NPC;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.entities.Wizard;
import com.rayanistan.game.utils.BoundedCamera;
import com.rayanistan.game.utils.CameraUtils;
import com.rayanistan.game.utils.WorldUtils;
import org.w3c.dom.css.Rect;

import static com.rayanistan.game.NotTextAdventure.*;
import static com.rayanistan.game.utils.WorldUtils.Constants.*;

public class PlayState extends AbstractState {

    // Declare player class that holds a box2d body with a sprite to render over it
    private Player player;
    private Array<NPC> npcs;

    // Box2D world handles physics simulation
    private World world;

    // Re-purpose camera into bounded camera to avoid scrolling offscreen
    private BoundedCamera cam;

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

        this.cam = new BoundedCamera();
        this.cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

        // Make extend viewport to maintain aspect ration
        viewport = new ExtendViewport(V_WIDTH, V_HEIGHT, cam);

        npcs = new Array<NPC>();
    }

    @Override
    public void show() {

        tileMap = app.assets.get("maps/stage1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tileMap);

        int levelWidth = tileMap.getProperties().get("width", Integer.class);
        int levelHeight = tileMap.getProperties().get("height", Integer.class);
        int tileSize = 64;

        this.cam.setBounds(0, levelWidth * tileSize, 0, levelHeight * tileSize);

        player = WorldUtils.createPlayer(world, app.assets.get("sprites/player.atlas",
                TextureAtlas.class), tileMap.getLayers().get("entities"));

        WorldUtils.createEntities(world, app.assets.get("sprites/npc.atlas", TextureAtlas.class),
                tileMap.getLayers().get("entities"), npcs, GROUND_BITS);

        WorldUtils.createCollisions(world, tileMap.getLayers().get("box2d"), (short) (WIZARD_BITS | PLAYER_BITS));


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

        renderer.setView(cam);
    }

    private void cameraUpdate() {
        CameraUtils.lerpToTarget(cam, player.getCenter());

        Wizard wizard = (Wizard) npcs.get(0);

        // If the wizard is within 1 meter of the player
        // then interpolate the position of the camera
        // between the average of the two objects
        if (wizard.getCenter().dst(player.getCenter()) < 1 * PPM) {
            CameraUtils.lerpAverage(cam, player.getCenter(), wizard.getCenter());
        }
    }

    @Override
    public void render(float dt) {
        super.render(dt);

        // Set clear color to white
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        // Set sprite batch's projection matrix4 to the camera.projection [matrix4] * camera.view [matrix4]
        app.batch.setProjectionMatrix(cam.combined);

        renderer.render();

        // Begin sprite batch operations
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
