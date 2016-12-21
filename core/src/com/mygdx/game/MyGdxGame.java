package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {

	// Declare batches for drawing
	private SpriteBatch batch;

	// Declare animation + texture atlas + sprite + textures
	private Animation animation2;
	private TextureAtlas textureAtlas2;
	private Sprite sprite;
	private Texture texture;
	private Texture weaponTexture;

	private float elapsedTime = 0;

	private OrthographicCamera camera;
	private boolean isEquipped = false;


	private enum Direction {
		RIGHT,
		LEFT,
		STILL
	}


	private Direction direction;

	@Override
	public void create() {
		batch = new SpriteBatch();

		textureAtlas2 = new TextureAtlas(Gdx.files.internal("walk2-packed/pack.atlas"));
		animation2 = new Animation(1 / 11f, textureAtlas2.findRegions("run"));

		texture = new Texture(Gdx.files.internal("stand.png"));
		sprite = new Sprite(texture);

		weaponTexture = new Texture(Gdx.files.internal("SWORD.png"));

		direction = Direction.STILL;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}


	int x = 0;

	@Override
	public void render() {


		Gdx.gl.glClearColor(255, 255, 255, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		Vector3 position = camera.position;
		position.x = MathUtils.lerp(position.x, sprite.getX() + sprite.getWidth() / 2, 0.1f);
		position.y = MathUtils.lerp(position.y, sprite.getY() + sprite.getHeight() / 2, 0.1f);

		camera.position.set(position);
		camera.update();


		batch.setProjectionMatrix(camera.combined);



        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            isEquipped = !isEquipped;
        }
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += 2;
			TextureRegion frame = animation2.getKeyFrame(elapsedTime, true);
			sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
			sprite.setRegion(frame);
			direction = Direction.RIGHT;
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			x -= 2;
			TextureRegion frame = animation2.getKeyFrame(elapsedTime, true);
			sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
			sprite.setRegion(frame);
			direction = Direction.LEFT;
		} else {
			sprite.setBounds(0, 0, texture.getWidth(), texture.getHeight());
			sprite.setRegion(texture);
		}


		//TODO: ^THE IF STATEMENT SHOULD CHANGE THE TEXTURE PERMANENTElY, UNTIL USER RE-CLICKS THE RIGHT BUTTON

		switch (direction) {
			default:
			case STILL:
				sprite.setFlip(false, false);
				break;
			case LEFT:
				sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setFlip(true, false);
				break;
		}

		if (isEquipped) {
			sprite.setBounds(0, 0, weaponTexture.getWidth(), weaponTexture.getHeight());
			sprite.setRegion(weaponTexture);
		}

		sprite.setX(x);
		sprite.setY(170);


		batch.begin();
		sprite.draw(batch);
		batch.end();

		elapsedTime += Gdx.graphics.getDeltaTime();
	}
	@Override
	public void resize ( int width, int height){

		camera.setToOrtho(false, width / 2, height / 2);
	}


	@Override
	public void dispose(){
		batch.dispose();
		texture.dispose();
		textureAtlas2.dispose();
		weaponTexture.dispose();
	}


}

