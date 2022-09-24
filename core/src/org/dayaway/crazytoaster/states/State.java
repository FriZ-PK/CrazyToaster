package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.dayaway.crazytoaster.CrazyToaster;

public abstract class State {

    protected Camera camera;
    protected Viewport viewport;
    protected GameStateManager gsm;

    public State (GameStateManager gsm) {
        this.gsm = gsm;
        this.camera = gsm.getCamera();
        this.viewport = gsm.getViewport();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
    public abstract void resize(int width, int height);
}
