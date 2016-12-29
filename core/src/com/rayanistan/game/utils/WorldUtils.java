package com.rayanistan.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.entities.NPC;
import com.rayanistan.game.entities.Player;
import com.rayanistan.game.entities.Wizard;

import java.util.Map;

import static com.rayanistan.game.utils.WorldUtils.Constants.GROUND_BITS;
import static com.rayanistan.game.utils.WorldUtils.Constants.PLAYER_BITS;
import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class WorldUtils {

    public static void createCollisions(World world, MapLayer layer, short mask) {
        for (MapObject mo : layer.getObjects()) {
            if (mo instanceof RectangleMapObject) {
                Rectangle bounds = ((RectangleMapObject) mo).getRectangle();

                createBox(world, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2 - 1,
                        bounds.getWidth(), bounds.getHeight(), true, "ground", GROUND_BITS, mask);
            } else if (mo instanceof PolylineMapObject) {
                Polyline bounds = ((PolylineMapObject) mo).getPolyline();
                float[] vertices = bounds.getTransformedVertices();

                createPolyline(world, vertices, true, mask, GROUND_BITS, "border");
            }
        }
    }


    public static void createEntities(World world, AssetManager assets, MapLayer layer, Map<String, NPC> npcs, short mask) {
        for (MapObject mo : layer.getObjects()) {
            if (mo instanceof RectangleMapObject) {
                if (mo.getName().equals("Wizard")) {
                    Rectangle bounds = ((RectangleMapObject) mo).getRectangle();

                    npcs.put("wizard", new Wizard(world, assets.get("sprites/npc.atlas", TextureAtlas.class), bounds, mask));
                }
            }
        }
    }

    public static Player createPlayer(World world, AssetManager assets, MapLayer layer) {
        // Enumerate until find player spawn
        Player player = null;
        for (MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)) {
            if (mo.getName().equals("PlayerSpawn")) {
                float x = ((RectangleMapObject) mo).getRectangle().getX();
                float y = ((RectangleMapObject) mo).getRectangle().getY();
                player = new Player(world, assets.get("sprites/player.atlas", TextureAtlas.class), x, y);
            }
        }
        return player;
    }

    // Create a class for the constants used in Box2D
    public final class Constants {
        // Pixel per meter ratio, used to convert Box2D units over to screen units
        public final static int PPM = 32;

        // World step, How many times should the physics world update
        public final static float STEP = 1 / 60f;

        // Velocity Iterations and Position Iterations, don't know too much about
        public final static int VELOCITY_ITERATIONS = 6;
        public final static int POSITION_ITERATIONS = 2;

        // Used for collision bits
        public final static short PLAYER_BITS = 2;
        public final static short WIZARD_BITS = 4;
        public final static short GROUND_BITS = 8;
    }

    // Use this to create a box2d body with a fixture in the shape of square
    public static Body createBox(World world, float x, float y, float width,
                                 float height, boolean isStatic, Object userData,
                                 short category, short mask) {
        Body body;
        BodyDef bodyDef = new BodyDef();

        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        if (isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        body.createFixture(fixtureDef).setUserData(userData);

        return body;
    }

    public static Body createPolyline(World world, float vertices[], boolean isStatic, short mask, short category, Object userData) {
        Body body;

        BodyDef bodyDef = new BodyDef();

        if (isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        Vector2[] actualVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < actualVertices.length; i++) {
            actualVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createChain(actualVertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cs;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;

        body.createFixture(fixtureDef).setUserData(userData);
        return body;
    }

    public static Body createPlayerBody(World world, float x, float y, float width, float height, Object userData) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(userData);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PLAYER_BITS;
        fixtureDef.filter.maskBits = GROUND_BITS;
        fixtureDef.density = 1.0f;
        body.createFixture(fixtureDef);

        shape.setAsBox(width / 2 / PPM, height / 8 / PPM, new Vector2(0, -height * 3 / 8 / PPM), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("player-foot");
        shape.dispose();

        return body;
    }
}
