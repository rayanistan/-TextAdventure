package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.rayanistan.game.components.FocalPointComponent;
import com.rayanistan.game.utils.Mappers;

public class CameraSystem extends IteratingSystem {

    private OrthographicCamera camera;

    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(FocalPointComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        FocalPointComponent focalPoint = Mappers.focalMapper.get(entity);

        if (focalPoint.current) {
            camera.position.interpolate(new Vector3(focalPoint.position.x, focalPoint.position.y, 0),
                    0.1f, Interpolation.linear);
            camera.update();
        }
    }
}
