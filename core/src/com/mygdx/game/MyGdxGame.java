package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;


	private Animation animation2;
	private TextureAtlas textureAtlas2;

	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;
    private BitmapFont font;
	private Sprite sprite;
	private Texture texture;



	private OrthographicCamera camera;


	@Override
	public void create () {
		batch = new SpriteBatch();

		//move right animation
		textureAtlas = new TextureAtlas(Gdx.files.internal("walk-packed/pack.atlas"));
		animation = new Animation(1/11f, textureAtlas.getRegions());

		//move left animation
		textureAtlas2 = new TextureAtlas(Gdx.files.internal("walk2-packed/pack.atlas"));
		animation2 = new Animation(1/11f, textureAtlas2.getRegions());

		//misc
        font = new BitmapFont();
		font.setColor(Color.WHITE);

		//when not moving
		texture = new Texture(Gdx.files.internal("stand.png"));
		sprite = new Sprite(texture);




		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
    int x = 0;

	@Override
	public void render () {


		Gdx.gl.glClearColor(255, 255, 255, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		Vector3 position = camera.position;
		position.x = MathUtils.lerp(position.x, sprite.getX()+sprite.getWidth()/2, 0.1f);
		position.y = MathUtils.lerp(position.y, sprite.getY()+sprite.getHeight()/2, 0.1f);
		camera.position.set(position);
		camera.update();


		batch.setProjectionMatrix(camera.combined);
		batch.begin();


		if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.D) ){
			sprite.draw(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			x+=2;
			batch.draw(animation.getKeyFrame(elapsedTime, true), x ,170 );

		}
		else if(Gdx.input.isKeyPressed(Input.Keys.A)){
         x-=2;
         batch.draw(animation2.getKeyFrame(elapsedTime, true), x, 170);

		}
		else if(!Gdx.input.isKeyPressed(Input.Keys.D)){
			sprite.draw(batch);
		}

		sprite.setX(x);
		sprite.setY(170);



		elapsedTime+= Gdx.graphics.getDeltaTime();

		batch.end();
	}
    @Override
	public void resize(int width, int height){

		camera.setToOrtho(false, width/2, height/2);
	}
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();
		font.dispose();
		texture.dispose();
		textureAtlas2.dispose();

	}
}
