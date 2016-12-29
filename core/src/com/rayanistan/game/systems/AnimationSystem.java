package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.rayanistan.game.components.AnimationComponent;
import com.rayanistan.game.components.StateComponent;
import com.rayanistan.game.components.TextureComponent;
import com.rayanistan.game.utils.Mappers;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class,
                StateComponent.class,
                TextureComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent anim = Mappers.animationMapper.get(entity);
        StateComponent state = Mappers.stateMapper.get(entity);

        if (anim.animations.containsKey(state.get())) {
            TextureComponent texture = Mappers.textureMapper.get(entity);
            texture.region = anim.animations.get(state.get()).getKeyFrame(state.timer, state.isLooping);
        }

        state.timer += deltaTime;
    }
}
