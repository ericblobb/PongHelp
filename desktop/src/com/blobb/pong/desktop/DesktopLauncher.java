package com.blobb.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blobb.pong.PongMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pong";
		config.width = 400;
		config.height = config.width / 4 * 3;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new PongMain(), config);
	}
}
