package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

import java.util.Random;

public class Floor {
    private final TextureRegion floorTexture;
    private float SPEED;
    private final Vector3 position;
    private final Vector3 velocity;

    //Пока что только для рандомноой установки направления тостера для одиночных платформ
    private boolean one;

    public Floor(TextureRegion floorTexture, float SPEED, float y) {
        this.floorTexture = floorTexture;
        this.SPEED = SPEED;
        Random random = new Random();
        int x = random.nextInt((CrazyToaster.WIDTH - floorTexture.getRegionWidth() - LevelManager.INDENT * 2) + 1) + LevelManager.INDENT;
        this.position = new Vector3(x, y,0);
        this.velocity = new Vector3();
    }

    public Floor(TextureRegion floorTexture, float SPEED, int y, boolean isOne) {
        this.floorTexture = floorTexture;
        this.SPEED = SPEED;
        Random random = new Random();
        int x = random.nextInt((CrazyToaster.WIDTH - floorTexture.getRegionWidth() - LevelManager.INDENT * 8) + 1) + LevelManager.INDENT*4;
        this.position = new Vector3(x, y,0);
        this.velocity = new Vector3();
        this.one = isOne;
    }

    public void update(float dt) {
        velocity.add(SPEED, 0, 0);
        velocity.scl(dt);
        position.add(velocity.x, 0, 0);

        if (position.x < LevelManager.INDENT) {
            SPEED *= -1;
            position.x = LevelManager.INDENT;
        } else if (position.x + floorTexture.getRegionWidth() > CrazyToaster.WIDTH - LevelManager.INDENT) {
            SPEED *= -1;
            position.x = CrazyToaster.WIDTH - LevelManager.INDENT - floorTexture.getRegionWidth();
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

    public TextureRegion getFloor() {
        return floorTexture;
    }

    public void shake() {
        position.x += (new Random().nextFloat() - 0.5f) * 2 * 4;
        position.y += (new Random().nextFloat() - 0.5f) * 2 * 4;
    }

    //Пока что только для рандомноой установки направления тостера для одиночных платформ
    public boolean isOne() {
        return one;
    }

    public void setFirstPlatformOnEndlessGame() {
        position.y = 0;
    }
}
