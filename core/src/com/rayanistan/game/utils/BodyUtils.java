package com.rayanistan.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class BodyUtils {

    // Pixel per meter ratio, used to convert Box2D units over to screen units
    public final static int PPM = 32;

    // World step, How many times should the physics world update
    public final static float STEP = 1 / 60f;

    // Velocity Iterations and Position Iterations, don't know too much about
    public final static int VELOCITY_ITERATIONS = 6;
    public final static int POSITION_ITERATIONS = 2;

    private final static String TAG = BodyUtils.class.getSimpleName();

    private static World world;

    public static void setWorld(World world) {
        BodyUtils.world = world;
    }

    public static Body readFromJson(FileHandle handle, Sprite sprite) {

        Body body;

        String rawJson = handle.readString();
        JsonValue root = new JsonReader().parse(rawJson);

        BodyDef bodyDef = new BodyDef();

        JsonValue jsonBodyDef = root.get("BodyDef");
        String bodyType = jsonBodyDef.getString("BodyType");

        // Check for bodyType
        if (bodyType.equalsIgnoreCase("DynamicBody"))
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        else if (bodyType.equalsIgnoreCase("StaticBody"))
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else if (bodyType.equalsIgnoreCase("KinematicBody"))
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        else
            Gdx.app.error(TAG, "BodyType is not a valid body type: " + bodyType);

        // Check for fixed rotation
        boolean fixedRotation = jsonBodyDef.getBoolean("fixedRotation");
        bodyDef.fixedRotation = fixedRotation;

        // Set position based off of rectangle
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) / PPM, (sprite.getY() + sprite.getHeight()) / PPM);

        bodyDef.angle = jsonBodyDef.getFloat("rotation") * MathUtils.degreesToRadians;

        body = world.createBody(bodyDef);

        // Handle all fixtures
        JsonValue fixtures = root.get("Fixtures");
        FixtureDef fixtureDef = new FixtureDef();

        for (JsonValue value : fixtures) {
            String shapeString = value.getString("shape");

            if (shapeString.equalsIgnoreCase("PolygonShape")) {
                PolygonShape shape = new PolygonShape();

                if (value.getString("descriptor").equalsIgnoreCase("main"))
                    shape.setAsBox(sprite.getWidth() * sprite.getScaleX() / PPM / 2,
                            sprite.getHeight() * sprite.getScaleY() / PPM / 2);

                else
                    shape.setAsBox(value.getFloat("width") / 2,
                            value.getFloat("height") / 2, new Vector2(value.getFloat("x"), value.getFloat("y")), 0);

                fixtureDef.shape = shape;
            }

            fixtureDef.density = value.getFloat("density");
            fixtureDef.isSensor = value.getBoolean("isSensor");

            body.createFixture(fixtureDef).setUserData(value.getString("descriptor"));
        }


        return body;
    }

    public static Body createFromRectangle(Rectangle bounds) {
        Body body;
        BodyDef bodyDef = new BodyDef();

        bodyDef.fixedRotation = true;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / PPM,
                (bounds.getY() + bounds.getHeight() / 2) / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bounds.getWidth() / 2 / PPM, bounds.getHeight() / 2 / PPM);
        fixtureDef.shape = shape;
        fixtureDef.density = 1.2f;

        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public static Body createFromVertices(Vector2[] vertices) {
        Body body;
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        ChainShape cs = new ChainShape();
        cs.createChain(vertices);
        fixtureDef.shape = cs;
        fixtureDef.density = 1.2f;

        body.createFixture(fixtureDef);

        return body;
    }
}
