package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.rayanistan.game.components.AnimationComponent;
import com.rayanistan.game.components.SpriteComponent;
import com.rayanistan.game.utils.Mappers;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class,
                SpriteComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent anim = Mappers.animationMapper.get(entity);
        SpriteComponent sprite = Mappers.spriteMapper.get(entity);

        sprite.sprite.setRegion(anim.getFrame());
        sprite.sprite.setSize(anim.getFrame().getRegionWidth(), anim.getFrame().getRegionHeight());

        anim.timer += deltaTime;
    }
}
