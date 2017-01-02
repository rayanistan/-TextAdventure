package com.rayanistan.game.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.rayanistan.game.components.*;

public final class Mappers {

    public final static ComponentMapper<SpriteComponent> spriteMapper =
            ComponentMapper.getFor(SpriteComponent.class);

    public final static ComponentMapper<AnimationComponent> animationMapper =
            ComponentMapper.getFor(AnimationComponent.class);

    public final static ComponentMapper<BodyComponent> physicsMapper =
            ComponentMapper.getFor(BodyComponent.class);

    public final static ComponentMapper<TransformComponent> transformMapper =
            ComponentMapper.getFor(TransformComponent.class);

    public final static ComponentMapper<FocalPointComponent> focalMapper =
            ComponentMapper.getFor(FocalPointComponent.class);

    public final static ComponentMapper<PlayerComponent> playerMapper =
            ComponentMapper.getFor(PlayerComponent.class);
}
