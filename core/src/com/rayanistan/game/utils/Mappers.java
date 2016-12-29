package com.rayanistan.game.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.rayanistan.game.components.*;

public final class Mappers {

    public final static ComponentMapper<TextureComponent> textureMapper =
            ComponentMapper.getFor(TextureComponent.class);

    public final static ComponentMapper<AnimationComponent> animationMapper =
            ComponentMapper.getFor(AnimationComponent.class);

    public final static ComponentMapper<StateComponent> stateMapper =
            ComponentMapper.getFor(StateComponent.class);

    public final static ComponentMapper<PhysicsComponent> physicsMapper =
            ComponentMapper.getFor(PhysicsComponent.class);

    public final static ComponentMapper<TransformComponent> transformMapper =
            ComponentMapper.getFor(TransformComponent.class);

    public final static ComponentMapper<CameraComponent> cameraMapper =
            ComponentMapper.getFor(CameraComponent.class);
}
