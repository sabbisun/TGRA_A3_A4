package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.shapes.LabFirst3DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Lab1"; // or whatever you like
		config.width = 1440;  //experiment with
		config.height = 720;  //the window size
		config.x = 200;
		config.y = 100;
		//config.fullscreen = true;

		new LwjglApplication(new LabFirst3DGame(), config);
	}
}
