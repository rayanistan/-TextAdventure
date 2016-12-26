package com.rayanistan.game.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static com.rayanistan.game.utils.WorldUtils.Constants.GROUND_BITS;
import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class WorldUtils {

    public static void createCollisions(World world, MapLayer layer, short mask) {
        for (MapObject mo : layer.getObjects()) {
            if (mo instanceof RectangleMapObject) {
                Rectangle bounds = ((RectangleMapObject) mo).getRectangle();

                createBox(world, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2 - 1,
                        bounds.getWidth(), bounds.getHeight(), true, null, GROUND_BITS, mask);
            }
            else if (mo instanceof PolylineMapObject) {

            }
            else if (mo instanceof PolygonMapObject) {

            }
        }
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

}
