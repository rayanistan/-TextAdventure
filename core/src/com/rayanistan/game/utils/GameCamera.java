package com.rayanistan.game.utils;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class GameCamera extends OrthographicCamera {

    private float xmin;
    private float xmax;
    private float ymin;
    private float ymax;

    public GameCamera() {
        this(0, 0, 0, 0);
    }

    public GameCamera(float xmin, float xmax, float ymin, float ymax) {
        super();
        setBounds(xmin, xmax, ymin, ymax);
    }

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

    public void lerpToTarget(float x, float y) {
        Vector3 position = this.position;

        position.x = MathUtils.lerp(position.x, x, 0.1f);
        position.y = MathUtils.lerp(position.y, y, 0.1f);
        this.position.set(position);
        update();
    }

}