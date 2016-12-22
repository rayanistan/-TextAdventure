package com.rayanistan.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.rayanistan.game.NotTextAdventure;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.atlasExtension = ".atlas";
		TexturePacker.process(settings, "./frames", ".", "player");
		new LwjglApplication(new NotTextAdventure(), config);
	}
}
