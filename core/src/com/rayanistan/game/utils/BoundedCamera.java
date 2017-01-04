package com.rayanistan.game.utils;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;

import static com.rayanistan.game.utils.Constants.PPM;

public class BoundedCamera extends OrthographicCamera {

    private float xmin;
    private float xmax;
    private float ymin;
    private float ymax;

    public void setBounds(float xmin, float xmax, float ymin, float ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }

    public void update() {
        fixBounds();
        super.update();
    }

    private void fixBounds() {
        position.x = MathUtils.clamp(position.x, xmin + viewportWidth / 2, xmax - viewportWidth / 2);
        position.y = MathUtils.clamp(position.y, ymin + viewportHeight / 2, ymax - viewportHeight / 2);
    }

    public Matrix4 getDebugMatrix() {
        return this.combined.cpy().scl(PPM);
    }
}
