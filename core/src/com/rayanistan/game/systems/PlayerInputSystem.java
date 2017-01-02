package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.rayanistan.game.components.BodyComponent;
import com.rayanistan.game.components.FocalPointComponent;
import com.rayanistan.game.components.PlayerComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.Mappers;

import static com.badlogic.gdx.Input.Keys.*;

public class PlayerInputSystem extends IteratingSystem {

    public PlayerInputSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        TransformComponent transform = Mappers.transformMapper.get(entity);
        FocalPointComponent focalPoint = Mappers.focalMapper.get(entity);
        BodyComponent body = Mappers.physicsMapper.get(entity);
        PlayerComponent player = Mappers.playerMapper.get(entity);

        float speed = 350 * transform.scale.x * dt;

        if (Gdx.input.isKeyPressed(A)) {
            speed *= -1;
            transform.orientation = TransformComponent.Orientation.LEFT;
        } else if (Gdx.input.isKeyPressed(D)) {
            transform.orientation = TransformComponent.Orientation.RIGHT;
        } else {
            speed = 0;
            transform.velocity.x = 0;
        }

        if (Gdx.input.isKeyJustPressed(SPACE) && player.numOfContactsWithFoot > 0) {
            body.body.applyForceToCenter(0, 250, true);
        }

        body.body.setLinearVelocity(speed, body.body.getLinearVelocity().y);
        transform.velocity = body.body.getLinearVelocity();

        focalPoint.position = transform.position;
    }
}
