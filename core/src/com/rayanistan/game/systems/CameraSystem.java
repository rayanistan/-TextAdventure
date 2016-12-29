package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.rayanistan.game.components.CameraComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.Mappers;

public class CameraSystem extends IteratingSystem {

    public CameraSystem() {
        super(Family.all(CameraComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cam = Mappers.cameraMapper.get(entity);
        TransformComponent target = Mappers.transformMapper.get(cam.target);

        cam.camera.lerpToTarget(target.pos.x, target.pos.y);
    }
}
