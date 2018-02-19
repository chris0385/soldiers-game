package de.chris0385;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * TODO: put gui in separate module!
 */
public class Main {
	public static void main(String[] args) {
	      LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	      cfg.title = "MyGame";
	      cfg.useGL30 = false;
	      cfg.width = 480;
	      cfg.height = 320;
	      new LwjglApplication(new GraphicsDemo(), cfg);
	}
}
