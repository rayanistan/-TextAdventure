package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.BodyComponent;
import com.rayanistan.game.components.SpriteComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.Constants;
import com.rayanistan.game.utils.Mappers;

public class TransformSystem extends IteratingSystem {

    public TransformSystem() {
        super(Family.all(BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.physicsMapper.get(entity);
        TransformComponent transform = Mappers.transformMapper.get(entity);
        SpriteComponent sprite = Mappers.spriteMapper.get(entity);

        // BodyComponent => TransformComponent => SpriteComponent (TODO: Replace with TextureComponent)
        if (transform != null) {
            Vector2 position = body.body.getPosition();
            transform.position = position.scl(Constants.PPM);
            transform.rotation = body.body.getAngle() * MathUtils.radiansToDegrees;

            if (sprite != null) {
                sprite.sprite.setPosition(transform.position.x - sprite.sprite.getRegionWidth() / 2,
                        transform.position.y - sprite.sprite.getRegionHeight() / 2);

                sprite.sprite.setOriginCenter();
                sprite.sprite.setRotation(transform.rotation);
                sprite.sprite.setFlip(transform.orientation.value, false);
            }
        }
    }
}
