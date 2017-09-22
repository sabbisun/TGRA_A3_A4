package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.shapes.LabFirst3DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Lab1"; // or whatever you like
		config.width = 1920;  //experiment with
		config.height = 1080;  //the window size
		//config.x = 150;
		//config.y = 50;
		config.fullscreen = true;

		new LwjglApplication(new LabFirst3DGame(), config);
	}
}
