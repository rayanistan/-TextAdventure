package com.rayanistan.game.archetypes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.*;
import com.rayanistan.game.interfaces.Archetype;
import com.rayanistan.game.utils.BodyUtils;

public class PlayerArchetype implements Archetype {

    private AssetManager assets;
    private Vector2 position;

    public PlayerArchetype(AssetManager assets, Vector2 position) {
        this.assets = assets;
        this.position = position;
    }

    @Override
    public Entity buildEntity() {
        Entity player = new Entity();

        // Create Components
        AnimationComponent animation = new AnimationComponent();
        SpriteComponent sprite = new SpriteComponent();
        BodyComponent body = new BodyComponent();
        PlayerComponent component = new PlayerComponent();
        TransformComponent transform = new TransformComponent();
        FocalPointComponent focalPoint = new FocalPointComponent();

        // Create Animation + State for player
        Animation idle = new Animation(0.75f, assets.get("sprites/player.atlas",
                TextureAtlas.class).findRegions("nothing_idle"));

        Animation walking = new Animation(1/16f, assets.get("sprites/player.atlas",
                TextureAtlas.class).findRegions("walk"));

        Animation jumping = new Animation(1/11f, assets.get("sprites/player.atlas", TextureAtlas.class)
                .findRegions("jump"));

        animation.animations.put(AnimationComponent.State.IDLE, idle);
        animation.animations.put(AnimationComponent.State.WALKING, walking);
        animation.animations.put(AnimationComponent.State.JUMPING, jumping);

        animation.setAnimation(AnimationComponent.State.IDLE, true);

        // Create sprite by getting first frame of idle
        sprite.sprite.setRegion(animation.getFrame());
        sprite.sprite.setSize(sprite.sprite.getRegionWidth(), sprite.sprite.getRegionHeight());
        sprite.sprite.setPosition(position.x, position.y);

        transform.scale = new Vector2(1f, 1f);
        sprite.sprite.setScale(transform.scale.x, transform.scale.y);

        // Generate body by using readFromJson method with File of bodies/PlayerBody
        body.body = BodyUtils.readFromJson(new FileHandle("json/PlayerBody.json"), sprite.sprite);

        // Set focalPoint.current to true
        focalPoint.current = true;

        // Add components
        player.add(focalPoint);
        player.add(animation);
        player.add(sprite);
        player.add(body);
        player.add(component);
        player.add(transform);

        return player;
    }

    public static Entity spawnEntity(AssetManager assets, Vector2 location) {
        return (new PlayerArchetype(assets, location)).buildEntity();
    }
}
