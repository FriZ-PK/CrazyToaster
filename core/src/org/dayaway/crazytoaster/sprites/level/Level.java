package org.dayaway.crazytoaster.sprites.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;

public abstract class Level {

    private final Toaster toaster;
    private final Floor floor;

    public Level(Toaster toaster) {
        this.floor = toaster.getFloor();
        this.toaster = toaster;
    }

    protected void update(float dt) {
        floor.update(dt);
        toaster.update(dt);
    }

    protected void render(SpriteBatch batch) {
        floor.render(batch);
        toaster.render(batch);
    }

    protected void dispose() {
        toaster.dispose();
        floor.dispose();
    }

    public Toaster getToaster() {
        return toaster;
    }

    public Floor getFloor() {
        return this.floor;
    }

}
