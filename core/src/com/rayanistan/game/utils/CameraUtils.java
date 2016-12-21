package com.rayanistan.game.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by creat on 12/20/2016.
 */
public final class CameraUtils {

    public static void lerpToTarget(Camera cam, Vector2 target) {
        Vector3 position = cam.position;
        position.x = MathUtils.lerp(position.x, target.x, 0.1f);
        position.y = MathUtils.lerp(position.y, target.y, 0.1f);

        cam.position.set(position);
        cam.update();
    }
}
