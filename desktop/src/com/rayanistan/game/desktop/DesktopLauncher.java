package com.rayanistan.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.rayanistan.game.NotTextAdventure;

import static com.rayanistan.game.NotTextAdventure.HEIGHT;
import static com.rayanistan.game.NotTextAdventure.TITLE;
import static com.rayanistan.game.NotTextAdventure.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.atlasExtension = ".atlas";
		settings.wrapX = Texture.TextureWrap.ClampToEdge;
		settings.wrapY = Texture.TextureWrap.ClampToEdge;
		settings.stripWhitespaceX = true;
		settings.stripWhitespaceY = true;
		settings.edgePadding = true;
		settings.alias = true;
		settings.ignoreBlankImages = true;
		settings.pot = true;
		TexturePacker.process(settings, "./frames", ".", "player");

		config.title = TITLE;
		config.width = WIDTH;
		config.height = HEIGHT;

		new LwjglApplication(new NotTextAdventure(), config);
	}
}
