package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

import java.util.Random;

public class Floor {
    private final Texture floorTexture;
    private float SPEED;
    private final Vector3 position;
    private final Vector3 velocity;

    public Floor(Texture floorTexture, float SPEED, int y) {
        this.floorTexture = floorTexture;
        this.SPEED = SPEED;
        Random random = new Random();
        int x = random.nextInt((CrazyToaster.WIDTH - floorTexture.getWidth() - LevelManager.INDENT * 2) + 1) + LevelManager.INDENT;
        this.position = new Vector3(x, y,0);
        this.velocity = new Vector3();
    }

    public Floor(Texture floorTexture, float SPEED, int y, boolean isOne) {
        this.floorTexture = floorTexture;
        this.SPEED = SPEED;
        Random random = new Random();
        int x = random.nextInt((CrazyToaster.WIDTH - floorTexture.getWidth() - LevelManager.INDENT * 8) + 1) + LevelManager.INDENT*4;
        this.position = new Vector3(x, y,0);
        this.velocity = new Vector3();
    }

    public void update(float dt) {
        velocity.add(SPEED, 0, 0);
        velocity.scl(dt);
        position.add(velocity.x, 0, 0);

        if (position.x < LevelManager.INDENT) {
            SPEED *= -1;
            position.x = LevelManager.INDENT;
        } else if (position.x + floorTexture.getWidth() > CrazyToaster.WIDTH - LevelManager.INDENT) {
            SPEED *= -1;
            position.x = CrazyToaster.WIDTH - LevelManager.INDENT - floorTexture.getWidth();
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(floorTexture, position.x, position.y);
    }

    public void dispose() {
        //floorTexture.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getFloor() {
        return floorTexture;
    }

    public void shake() {
        position.x += (new Random().nextFloat() - 0.5f) * 2 * 4;
        position.y += (new Random().nextFloat() - 0.5f) * 2 * 4;
    }

}
