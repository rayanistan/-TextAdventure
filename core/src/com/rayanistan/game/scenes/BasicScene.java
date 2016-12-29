package com.rayanistan.game.scenes;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.rayanistan.game.entities.NPC;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.handlers.WorldContactHandler;
import com.rayanistan.game.states.PlayState;
import com.rayanistan.game.utils.WorldUtils;

import java.util.HashMap;
import java.util.Map;

import static com.rayanistan.game.NotTextAdventure.DEBUG;
import static com.rayanistan.game.NotTextAdventure.RENDER;
import static com.rayanistan.game.utils.WorldUtils.Constants.*;

public class BasicScene implements Disposable {

    public Map<String, NPC> NPCS;
    public Player player;
    public int level;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private PlayState state;

    public BasicScene(int level, PlayState state, World world, TiledMap map) {
        WorldContactHandler wch = new WorldContactHandler();
        this.level = level;
        this.state = state;
        this.world = world;
        this.world.setContactListener(wch);
        this.map = map;
        NPCS = new HashMap<String, NPC>();
        renderer = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new Box2DDebugRenderer();

        int levelWidth = map.getProperties().get("width", Integer.class);
        int levelHeight = map.getProperties().get("height", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);

        state.cam.setBounds(0, levelWidth * tileWidth, 0, levelHeight * tileHeight);

        player = WorldUtils.createPlayer(world, state.getApp().assets, map.getLayers().get("events"));
        player.wch = wch;

        WorldUtils.createEntities(world, state.getApp().assets, map.getLayers().get("entities"), NPCS, GROUND_BITS);

        WorldUtils.createCollisions(world, map.getLayers().get("collisions"), (short) (PLAYER_BITS | WIZARD_BITS));
    }

    public void update(float dt) {
        state.cam.lerpToTarget(player.getCenter().x, player.getCenter().y);

        world.step(STEP, POSITION_ITERATIONS, VELOCITY_ITERATIONS);

        for (NPC npc : NPCS.values()) {
            npc.update(dt);
        }

        player.update(dt);
    }

    public void render() {
        if (RENDER) {
            // Set sprite batch's projection matrix4 to the camera.projection [matrix4] * camera.view [matrix4]
            state.getApp().batch.setProjectionMatrix(state.cam.combined);

            renderer.setView(state.cam);
            renderer.render();

            state.getApp().batch.begin();

            // RENDER ORDER:
            // 0. TILEMAP
            // 1. PLAYER
            // 2. ALL NPCS
            // 3. ALL ENEMIES


            // Render all of the NPC's sprites
            for (NPC npc : NPCS.values()) {
                npc.render(state.getApp().batch);
            }

            // Render player
            player.render(state.getApp().batch);

            // Flush sprite batch to GPU
            state.getApp().batch.end();
        }


        // If DEBUG => render box2d debug lines up-scaled by Pixel Per Meter ratio
        if (DEBUG)
            debugRenderer.render(world, state.cam.combined.cpy().scl(PPM));
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        debugRenderer.dispose();
        player.dispose();
    }
}
