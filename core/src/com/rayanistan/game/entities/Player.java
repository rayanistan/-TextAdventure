package com.rayanistan.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.rayanistan.game.handlers.WorldContactHandler;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class Player implements Disposable {

    // Player entity needs to display a sprite for a visual representation, because players are so needy
    private Sprite sprite;

    // Create a Box2D body for a physical representation for the player
    private Body body;

    // This texture atlas will keep track of all of the players frames
    private TextureAtlas atlas;

    // Used this to keep track of which frame of the animation the sprite should show
    private float animationTimer = 0;
    public WorldContactHandler wch;

    // State enum to keep track of current frame to be displayed
    private enum State {
        NOTHING {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(1/11f, atlas.findRegions("walk")).getKeyFrame(timer, true);
            }
        },
        SWORD {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(1 / 11f, atlas.findRegions("s")).getKeyFrame(timer, true);
            }
        },
        NOTHING_IDLE {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(0.75f, atlas.findRegions("nothing_idle")).getKeyFrame(timer, true);
            }
        },
        JUMPING {
          public TextureRegion getFrame(TextureAtlas atlas, float timer) {
              return new Animation(1 / 11f, atlas.findRegions("jump")).getKeyFrame(timer, true);
          }
        },
        SWORD_IDLE {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(0.75f, atlas.findRegions("sword_idle")).getKeyFrame(timer, true);
            }
        };

        public abstract TextureRegion getFrame(TextureAtlas atlas, float timer);
    }

    // Keep track of current and previous screens
    private State previous;
    private State current;

    // Boolean to keep track if the sprite should be flipped
    private boolean flipped = false;

    public Player(World world, TextureAtlas atlas, float x, float y) {

        this.atlas = atlas;


        current = State.NOTHING;
        previous = State.NOTHING_IDLE;

        sprite = new Sprite();

        this.body = WorldUtils.createPlayerBody(world, x, y, 32, 32,this);
    }

    // Update animation, input, and sprite position relative to the box2d physics body
    public void update(float dt) {

        // Update current frame dependent on state
        handleAnimation(dt);

        // Set sprite position to the bottom left corner of the box2d box
        sprite.setPosition(body.getPosition().x * PPM - (sprite.getWidth() * sprite.getScaleX() / 2),
                body.getPosition().y * PPM - (sprite.getHeight() * sprite.getScaleY() / 2) - 1);

        // Set sprite origin to the center of the box2d box
        sprite.setOrigin(body.getPosition().x, body.getPosition().y);

        // Set scale of player sprite
        sprite.setScale(.90f, .90f);

        // Handle key inputs to change state and shift box2d body
        controller(dt);

    }

    private void handleAnimation(float dt) {
        // If the player change screens => reset animationTimer
        if (previous != current) {
            animationTimer = 0;
        }

        // Set sprite's frame to the current animation's frame
        sprite.setRegion(current.getFrame(atlas, animationTimer));

        // Set sprite size to the width and height of the frame
        sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());

        if (previous != current && current != State.NOTHING_IDLE && current != State.SWORD_IDLE) {
            // Change body for sprite size if the state has changed
            PolygonShape shape = (PolygonShape) body.getFixtureList().first().getShape();
            shape.setAsBox(sprite.getWidth() / 2 / PPM * sprite.getScaleX(),
                    sprite.getHeight() / 2 / PPM * sprite.getScaleY());
        }

        // Add delta time to the animation timer
        animationTimer += dt;

        // If flipped => then flip sprite
        sprite.setFlip(flipped, false);
    }

    private void controller(float dt) {
        // Make previous state equal to current state before changing state
        previous = current;

        // MOVEMENT
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            flipped = true;
            body.setLinearVelocity(-250 * dt, body.getLinearVelocity().y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            flipped = false;
            body.setLinearVelocity(250 * dt, body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }


        // JUMPING
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (wch.getNumOfContacts() > 0) {
                body.applyForceToCenter(0, 250, true);
                current = State.JUMPING;
            }
        }

        // SWITCHING BETWEEN SWORD AND NON SWORD
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            current = current == State.NOTHING || current == State.NOTHING_IDLE ? State.SWORD : State.NOTHING;
        }

        // IF IDLE SWITCH TO IDLE VERSION OF STATE
        if (body.getLinearVelocity().x == 0) {
            switch (current) {
                case SWORD:
                    current = State.SWORD_IDLE;
                    break;
                case JUMPING:
                case NOTHING:
                    current = State.NOTHING_IDLE;
            }
        } else {
            switch (current) {
                case SWORD_IDLE:
                    current = State.SWORD;
                    break;
                case JUMPING:
                case NOTHING_IDLE:
                    current = State.NOTHING;
                    break;
            }
        }

        if (wch.getNumOfContacts() > 0) {
            if (current == State.JUMPING) {
                current = State.NOTHING_IDLE;
            }
        }

    }

    public void render(SpriteBatch batch) {
        // When render method is called, draw to sprite to batch
        sprite.draw(batch);
    }

    public Vector2 getCenter() {
        // Used to get the position of box2d body
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public void dispose() {
        atlas.dispose();
        sprite.getTexture().dispose();
    }
}
