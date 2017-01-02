package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.BodyComponent;
import com.rayanistan.game.components.SpriteComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.BodyUtils;
import com.rayanistan.game.utils.Mappers;

public class TransformSystem extends IteratingSystem {

    public TransformSystem() {
        super(Family.all(BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = Mappers.spriteMapper.get(entity);
        BodyComponent body = Mappers.physicsMapper.get(entity);
        TransformComponent transform = Mappers.transformMapper.get(entity);

        // BodyComponent => TransformComponent => SpriteComponent
        if (transform != null) {
            Vector2 position = body.body.getPosition();
            transform.position = position.scl(BodyUtils.PPM);
            transform.rotation = body.body.getAngle() * MathUtils.radiansToDegrees;

            if (sprite != null) {
                Vector2 spritePosition = transform.position.sub(sprite.sprite.getWidth() * transform.scale.x / 2,
                        sprite.sprite.getHeight() * transform.scale.y / 2);

                sprite.sprite.setPosition(spritePosition.x, spritePosition.y);
                sprite.sprite.setRotation(transform.rotation);
                sprite.sprite.setFlip(transform.orientation.spriteFlip, false);
                sprite.sprite.setScale(transform.scale.x, transform.scale.y);
            }
        }


    }
}
