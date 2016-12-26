package com.rayanistan.game.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class CameraUtils {

    // Use linear interpolation to shift camera towards a target position
    public static void lerpToTarget(Camera cam, Vector2 target) {
        Vector3 position = cam.position;
        position.x = MathUtils.lerp(position.x, target.x, 0.1f);
        position.y = MathUtils.lerp(position.y, target.y, 0.1f);

        cam.position.set(position);
        cam.update();
    }

    // Use linear interpolation too shift camera between two positions
    public static void lerpAverage(Camera cam, Vector2 target1, Vector2 target2) {
        Vector2 targetAvg = new Vector2();
        targetAvg.x = (target1.x + target2.x) / 2;
        targetAvg.y = (target1.y + target2.y) / 2;
        lerpToTarget(cam, targetAvg);
    }
}
