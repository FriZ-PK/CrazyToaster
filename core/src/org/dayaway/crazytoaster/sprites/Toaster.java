package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.animation.AnimationPack;
import org.dayaway.crazytoaster.sprites.animation.ToasterAnimationManager;

import java.util.Random;

public class Toaster {

    private float SPEED;
    private final int SPEED_INCREMENTING = 5;
    private final Vector3 position;
    private final Vector3 velocity;
    private final Rectangle rectanglePosition;
    private final Floor floor;

    private boolean full;
    private boolean eat = false;
    private boolean shot = false;

    private ToasterAnimationManager tam;

    private final Random rand = new Random();

    public Toaster(Floor floor, float SPEED, boolean full, AnimationPack pack) {
        this.floor = floor;
        this.full = full;

        this.SPEED = SPEED;

        if (rand.nextBoolean()) {
            this.SPEED *= -1;
        }
        tam = new ToasterAnimationManager(this, pack);

        int x = (int) (rand.nextInt((floor.getFloor().getRegionWidth() - tam.getFrame().getRegionWidth()) + 1) + floor.getPosition().x);
        position = new Vector3(x, floor.getPosition().y + floor.getFloor().getRegionHeight() - 20, 0);

        rectanglePosition = new Rectangle(x + (tam.getFrame().getRegionWidth() - Toast.TOAST_PHYSIC_WIDTH)/2f,position.y,
                Toast.TOAST_PHYSIC_WIDTH, tam.getFrame().getRegionHeight()/2f);

        velocity = new Vector3(0,0,0);

    }

    public void update(float dt) {
        tam.update(dt);

        velocity.add(SPEED, 0, 0);
        velocity.scl(dt);
        position.add(velocity.x, 0, 0);

        position.y = floor.getPosition().y + floor.getFloor().getRegionHeight() - 20;

        if (position.x < floor.getPosition().x) {
            SPEED *= -1;
            position.x = floor.getPosition().x;
            tam.setDirection();
        } else if (position.x + tam.getFrame().getRegionWidth() > floor.getPosition().x + floor.getFloor().getRegionWidth()) {
            SPEED *= -1;
            position.x = floor.getPosition().x + floor.getFloor().getRegionWidth() - tam.getFrame().getRegionWidth();
            tam.setDirection();
        }
        rectanglePosition.x = position.x + (tam.getFrame().getRegionWidth() - Toast.TOAST_PHYSIC_WIDTH)/2f;
        rectanglePosition.y = position.y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(tam.getFrame(), position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getToaster() {
        return tam.getFrame();
    }

    public Rectangle getRectanglePosition() {
        return rectanglePosition;
    }

    public void reposition(float y) {
        position.x = rand.nextInt(CrazyToaster.WIDTH - tam.getFrame().getRegionWidth() * 3) + tam.getFrame().getRegionWidth();
        position.y = y;

        rectanglePosition.x = position.x + (tam.getFrame().getRegionWidth() - Toast.TOAST_PHYSIC_WIDTH)/2f;
        rectanglePosition.y = position.y;
    }

    public void incrementSPEED() {
        this.SPEED += SPEED_INCREMENTING;
        this.velocity.set(SPEED, 0, 0);
    }

    public void dispose() {
        tam.dispose();
    }

    //True - RIGHT_MOVE
    //False - LEFT_MOVE
    public boolean getDirection() {
        //Если это одиночная платформа, то скорость всегда будет равно 0 и нужно направление рандомить
        if(getFloor().isOne()) {
            return  rand.nextBoolean();
        }

        return SPEED > 0;
    }

    public void eating() {
        this.full = true;
        this.eat = true;
        tam.eating();
    }

    public boolean isEat() {
        return this.eat;
    }

    public void release() {
        this.shot = false;
        this.full = false;
        this.eat = false;
        tam.setDirection();
    }

    public void wellFed() {
        this.eat = false;
        tam.setDirection();
    }

    public boolean isFull() {
        return this.full;
    }

    public boolean isShot() {
        return this.shot;
    }

    public void fire() {
        this.shot = true;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void restartForReward() {
        this.full = true;
        tam.setDirection();
    }

}
