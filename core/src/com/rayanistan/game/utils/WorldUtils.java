package com.rayanistan.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class WorldUtils {

    public final class Constants {
        public final static int PPM = 32;
    }

    public static Body createBox(World world, float x, float y, float width,
                                 float height, boolean isStatic, Object userData) {
        Body body;
        BodyDef bodyDef = new BodyDef();

        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        if (isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        body.createFixture(shape, 1.0f).setUserData(userData);

        return body;
    }

}
