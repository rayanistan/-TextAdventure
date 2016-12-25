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
		// Create configuration for application
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Tell texture packer to create a player.atlas for all of the player frames in /sprites/player_frames
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.atlasExtension = ".atlas"; // Set extension to .atlas
		settings.wrapX = Texture.TextureWrap.ClampToEdge;
		settings.wrapY = Texture.TextureWrap.ClampToEdge;
		settings.stripWhitespaceX = true; // Cut of whitespace on the X axis
		settings.stripWhitespaceY = true; // Cut of whitespace on the Y axis
		settings.edgePadding = true; // Have a padding of 2
		settings.alias = true; // Have aliases (not using though)
		settings.ignoreBlankImages = true; // Ignore images that don't have anything
		settings.pot = true; // Enforce power of 2 for width and height
		TexturePacker.process(settings, "./sprites/player_frames", "./sprites", "player");

		// Tell texture packer to create a npc.atlas for all of the frames in /sprites/npc_frames
        TexturePacker.process(settings, "./sprites/npc_frames", "./sprites", "npc");

		// Setting application settings to the constants defined in NotTextAdventure.java
		config.title = TITLE; // TITLE
		config.width = WIDTH; // WIDTH
		config.height = HEIGHT; // HEIGHT

		// Create Application
		new LwjglApplication(new NotTextAdventure(), config);
	}
}
