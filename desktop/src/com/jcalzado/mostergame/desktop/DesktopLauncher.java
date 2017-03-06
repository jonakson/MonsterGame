package com.jcalzado.mostergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jcalzado.mostergame.MonsterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Monster Game";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new MonsterGame(), config);
	}
}
