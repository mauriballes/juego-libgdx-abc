package com.losdelcallejon.gamesmachine.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.losdelcallejon.gamesmachine.AbcGameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1600;
		config.height=900;
		new LwjglApplication(new AbcGameMain(null), config);
	}
}
