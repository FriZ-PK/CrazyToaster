package org.dayaway.crazytoaster;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.dayaway.crazytoaster.utill.ActionAd;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher implements ActionAd {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(480, 800);
		config.setForegroundFPS(60);
		config.setTitle(CrazyToaster.TITLE);
		new Lwjgl3Application(new CrazyToaster(new DesktopLauncher()), config);

		//УПАКОВЩИК ТЕКСТУР
		/*TexturePacker.Settings settings=new TexturePacker.Settings();
		settings.alias=true;
		settings.flattenPaths=true;
		TexturePacker.process(settings, "D:\\img\\toasters", "D:\\img\\output","macken.pack");*/

	}

	@Override
	public void showAd() {

	}

	@Override
	public void showRewardAd() {

	}
}
