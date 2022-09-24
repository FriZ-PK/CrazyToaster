package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.animation.TimerAnimationManager;
import org.dayaway.crazytoaster.sprites.animation.ToastAnimationManager;
import org.dayaway.crazytoaster.sprites.level.LevelCucumberPlatform;
import org.dayaway.crazytoaster.sprites.level.LevelManager;
import org.dayaway.crazytoaster.states.PlayState;

public class Toast {

    private final int SPEED = 600;
    private final int SPEED_FALL = -250;

    private long TIME_SINCE_CAPTURE;
    private long COOKING_TIME = 8000;
    private long COOKING_TIME_DECREMENTING = 0;

    //ФАКТИЧЕСКАЯ ВЫСОТА ТОСТА
    public static final int TOAST_PHYSIC_HEIGHT = 30;
    //ФАКТИЧЕСКАЯ ШИРИНА ТОСТА
    public static final int TOAST_PHYSIC_WIDTH = 34;

    private final Vector3 position;
    private final Vector3 velocity;
    private final Rectangle rectanglePosition;

    private final LevelManager levelManager;

    private boolean CAPTURED = true;
    private boolean FALL = false;
    private boolean lost = false;

    private Toaster toaster;
    private final PlayState playState;

    private final TimerAnimationManager timerAnimationManager;
    private final ToastAnimationManager toastAnimationManager;

    public Toast(LevelManager levelManager, PlayState ps) {
        TIME_SINCE_CAPTURE = System.currentTimeMillis();
        this.playState = ps;

        this.toaster = levelManager.getFullToaster();
        this.levelManager = levelManager;
        this.position = new Vector3(toaster.getPosition());

        this.rectanglePosition = new Rectangle(position.x + (CrazyToaster.textures.toast.getRegionWidth() - TOAST_PHYSIC_WIDTH)/2f,position.y,
                TOAST_PHYSIC_WIDTH,TOAST_PHYSIC_HEIGHT);

        this.velocity = new Vector3(0,0,0);

        timerAnimationManager = new TimerAnimationManager(ps);

        toastAnimationManager = new ToastAnimationManager(this);
    }

    public void update(float dt) {
        toastAnimationManager.update(dt);

        timerAnimationManager.update(dt);

        //Если тост в тостере то тост всегда выравнивается по нему
        if (CAPTURED) {
            position.x = toaster.getPosition().x;
            position.y = toaster.getPosition().y;

            //Если истекло время тост сгорает
            if(System.currentTimeMillis() - TIME_SINCE_CAPTURE > COOKING_TIME) {
                setToaster();
                timerAnimationManager.inverse();
                playState.setGAME_OVER(true);
                playState.turnEXPLODED();
            }

            //Градусник начинает мигать если осталась половина времени
            if(System.currentTimeMillis() - TIME_SINCE_CAPTURE > COOKING_TIME/2f && !timerAnimationManager.isBlink()) {
                timerAnimationManager.blink();
            }

        }
        //Если не в тостере
        else {
            //Если тост еще может упасть на тостер
            if (!playState.isGAME_OVER()) {

                //Если тостер еще не пролетел мимо тостера
                if (!FALL) {
                    velocity.add(0, SPEED, 0);
                    velocity.scl(dt);
                    position.add(0, velocity.y, 0);

                    //Если верхняя граница тоста выше верхней границы тостера + высота тостера, то тост отправляется в падение
                    if (position.y + CrazyToaster.textures.toast.getRegionHeight() > toaster.getPosition().y + (toaster.getToaster().getRegionHeight() * 2.5)) {
                        FALL = true;
                    }

                }
                //Если тост уже падает вниз
                else {
                    velocity.add(0, SPEED_FALL, 0);
                    velocity.scl(dt);
                    position.add(0, velocity.y, 0);


                    //Если тост пересекатеся с тостером
                    if (rectanglePosition.overlaps(toaster.getRectanglePosition()) && position.y > toaster.getPosition().y) {
                        //Тост пойман, падение заканчивается
                        if(playState.getGSM().isSoundOn()) {
                            CrazyToaster.textures.grab_sound.play();
                        }
                        grab();
                        FALL = false;

                    }
                    //Если тост ниже тостера
                    if(position.y < toaster.getPosition().y) {
                        velocity.scl(0.6f/dt);
                        //Если упал мимо тостера то GAME OVER
                        if (position.y < levelManager.getPreviousToaster().getFloor().getPosition().y +
                                levelManager.getPreviousToaster().getFloor().getFloor().getRegionHeight() - 20) {
                            position.y = levelManager.getPreviousToaster().getFloor().getPosition().y +
                                    levelManager.getPreviousToaster().getFloor().getFloor().getRegionHeight() - 20;

                            if (!lost) {
                                lost = true;
                                levelManager.shake();
                                toastAnimationManager.setAnimation();
                                playState.turnCRASHED();

                                if(playState.getGSM().isSoundOn()) {
                                    CrazyToaster.textures.smash_sound.play();
                                }
                            }
                        }
                    }
                }
            }

        }

        //Устанавливаем скелет тоста
        //По X позиция скелета тоста = (Ширина тоста - визическая ширина тоста)/2
        rectanglePosition.x = position.x + (CrazyToaster.textures.toast.getRegionWidth() - TOAST_PHYSIC_WIDTH)/2f;
        //По высоте скелет тоста равен реальной высоте тоста
        rectanglePosition.y = position.y;
    }

    public void render(SpriteBatch batch) {

        //Если тост не пойман, он рисуется
        if (!CAPTURED) {
            batch.draw(toastAnimationManager.getFrame(), position.x, position.y);
        }
        if(playState.isSTART()) {
            timerAnimationManager.render(batch);
        }
    }

    public Vector3 getPosition() {
        return position;
    }



    private void setToaster() {
        this.toaster = levelManager.nextToaster();
    }

    public void release() {
        timerAnimationManager.inverse(1);
        timerAnimationManager.unBlink();
        //Освобождаем тостер
        toaster.fire();
        this.position.y += toaster.getToaster().getRegionHeight()/ 2f;
        this.CAPTURED = false;
        setToaster();

    }

    public void grab() {
        //Если это проходят уровень, то нужно вовремя закончить
        if (playState.getLevelStatic() != null) {
            if (levelManager.getCountLevel() > playState.getLevelStatic().get().size() + 1) {
                playState.incrementScore();
                playState.win();
                return;
            }

        }
        levelManager.reposition();
        if (levelManager.getThisLevel() instanceof LevelCucumberPlatform) {
            setCOOKING_TIME(4000);
        }
        toaster.eating();
        this.CAPTURED = true;
        timerAnimationManager.inverse();
        timerAnimationManager.refresh();
        timerAnimationManager.unBlink();
        setTIME_SINCE_CAPTURE();
        playState.blink();
        playState.incrementScore();
        decrementCOOKING_TIME();
    }

    public boolean isCaptured() {
        return CAPTURED;
    }

    public void decrementCOOKING_TIME() {
        this.COOKING_TIME -= COOKING_TIME_DECREMENTING;
    }

    public void dispose() {

    }

    public void setTIME_SINCE_CAPTURE() {
        this.TIME_SINCE_CAPTURE = System.currentTimeMillis();
    }

    public void setCOOKING_TIME(long COOKING_TIME) {
        this.COOKING_TIME = COOKING_TIME;
        this.timerAnimationManager.setCycleTime(COOKING_TIME/1000f);
    }


    //Если посмотрели рекламу, что-бы продолжить
    public void restartForReward() {
        this.toaster = levelManager.getPreviousToaster();
        levelManager.restartForReward();
        position.x = toaster.getPosition().x;
        position.y = toaster.getPosition().y;
        FALL = false;
        CAPTURED = true;
        lost = false;
        toastAnimationManager.setAnimation();
        timerAnimationManager.inverse();
        timerAnimationManager.unBlink();
    }

    public boolean isLost() {
        return lost;
    }

}
