package org.dayaway.crazytoaster;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dayaway.crazytoaster.sprites.Textures;
import org.dayaway.crazytoaster.states.GameStateManager;
import org.dayaway.crazytoaster.states.PlayState;
import org.dayaway.crazytoaster.states.StartPageState;
import org.dayaway.crazytoaster.utill.ActionAd;

public class CrazyToaster extends ApplicationAdapter {
	private final ActionAd actionAd;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Crazy Toaster";

	public static float LEFT;
	public static float RIGHT;
	public static float BOTTOM;
	public static float TOP;
	public static float WIDTH_SCREEN;
	public static float HEIGHT_SCREEN;

	private GameStateManager gsm;

	private SpriteBatch batch;

	public static Textures textures;

	public CrazyToaster(ActionAd actionAd) {
		this.actionAd = actionAd;

	}
	
	@Override
	public void create () {
		textures = new Textures();
		batch = new SpriteBatch();
		batch.maxSpritesInBatch = 12;
		gsm = new GameStateManager(actionAd);
		gsm.push(new StartPageState(gsm));

	}

	@Override
	public void render () {
			gsm.update(Gdx.graphics.getDeltaTime());
			gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gsm.pop();
		textures.dispose();
	}

	@Override
	public void resize(int width, int height){
		gsm.resize(width, height);
	}

	public void turnREWARD_FOR_AD() {
		gsm.turnREWARD_FOR_AD();
	}



}
