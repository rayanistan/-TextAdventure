package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.rayanistan.game.components.AnimationComponent;
import com.rayanistan.game.components.PlayerComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.Mappers;

public class PlayerStateSystem extends IteratingSystem {

    public PlayerStateSystem() {
        super(Family.all(AnimationComponent.class,
                PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = Mappers.animationMapper.get(entity);
        TransformComponent transform = Mappers.transformMapper.get(entity);
        PlayerComponent player = Mappers.playerMapper.get(entity);

        if (player.numOfContactsWithFoot < 1) {
            animation.setAnimation(PlayerComponent.State.JUMPING);
        }

        else if (transform.velocity.x != 0 && animation.state != PlayerComponent.State.WALKING) {
            animation.setAnimation(PlayerComponent.State.WALKING);
        }
        else if (transform.velocity.x == 0 && animation.state != PlayerComponent.State.IDLING) {
            animation.setAnimation(PlayerComponent.State.IDLING);
        }

    }
}
