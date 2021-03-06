package com.rayanistan.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class BodyUtils {

    private final static String TAG = BodyUtils.class.getSimpleName();

    private static World world;

    public static void setWorld(World world) {
        BodyUtils.world = world;
    }

    public static Body readFromJson(FileHandle handle, Vector2 position) {

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
        bodyDef.fixedRotation = jsonBodyDef.getBoolean("fixedRotation");

        // Set position based off of rectangle
        bodyDef.position.set(position.x / Constants.PPM, position.y / Constants.PPM);

        bodyDef.angle = jsonBodyDef.getFloat("rotation") * MathUtils.degreesToRadians;

        body = world.createBody(bodyDef);

        // Handle all fixtures
        JsonValue fixtures = root.get("Fixtures");
        FixtureDef fixtureDef = new FixtureDef();

        for (JsonValue value : fixtures) {
            String shapeString = value.getString("shape");

            if (shapeString.equalsIgnoreCase("PolygonShape")) {
                PolygonShape shape = new PolygonShape();

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
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM,
                (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bounds.getWidth() / 2 / Constants.PPM, bounds.getHeight() / 2 / Constants.PPM);
        fixtureDef.shape = shape;
        fixtureDef.density = 1.2f;
        fixtureDef.friction = 0;

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
        fixtureDef.friction = 0;

        body.createFixture(fixtureDef);

        return body;
    }
}
