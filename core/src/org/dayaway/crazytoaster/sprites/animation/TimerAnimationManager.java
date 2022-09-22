package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;
import org.dayaway.crazytoaster.states.PlayState;

import java.text.DecimalFormat;

public class TimerAnimationManager {

    private final Animation timerAnim;

    private final PlayState playState;

    private boolean blink = false;
    private float BLINK_MULTIPLIER = 1f;

    private long startBlinkTime = 0;
    private long endBlinkTime = 0;

    public TimerAnimationManager(PlayState playState) {
        this.playState = playState;
        timerAnim = new Animation(new TextureRegion(CrazyToaster.textures.timer), 20,10f);
    }

    public void update(float dt) {
        timerAnim.update(dt);

        //Если таймер инвертирован
        if(timerAnim.isINVERSE()) {
            //Если последний фрейм, меняем на первый
            if(timerAnim.getCurrentFrameCount() == 19) {
                timerAnim.refresh();
            }
        }
    }

    public void inverse() {
        timerAnim.inverse();
    }

    public void inverse(float cycleTime) {
        timerAnim.inverse(cycleTime);
    }


    public void render(SpriteBatch batch) {

        if(!blink) {
            batch.draw(timerAnim.getFrame(), playState.getCamera().position.x - timerAnim.getFrame().getRegionWidth()/2f,
                    playState.getCamera().unproject(new Vector3(0,0,0)).y - timerAnim.getFrame().getRegionHeight() - LevelManager.LEVEL_GAP/3f - 40);
        }
        else {

            if(startBlinkTime == 0 && (System.currentTimeMillis() - endBlinkTime)/1000.0 > 0.5) {
                BLINK_MULTIPLIER = 1.3f;
                endBlinkTime = 0;
                startBlinkTime = System.currentTimeMillis();
            }
            else if ((System.currentTimeMillis() - startBlinkTime)/1000.0 > 0.5 && endBlinkTime == 0) {
                BLINK_MULTIPLIER = 1f;
                startBlinkTime = 0;
                endBlinkTime = System.currentTimeMillis();
            }

            batch.draw(timerAnim.getFrame(), playState.getCamera().position.x - timerAnim.getFrame().getRegionWidth() * BLINK_MULTIPLIER / 2f,
                    playState.getCamera().unproject(new Vector3(0, 0, 0)).y - timerAnim.getFrame().getRegionHeight() * BLINK_MULTIPLIER - LevelManager.LEVEL_GAP / 3f - 40,
                    timerAnim.getFrame().getRegionWidth() * BLINK_MULTIPLIER, timerAnim.getFrame().getRegionHeight() * BLINK_MULTIPLIER);
        }
    }

    public void refresh() {
        timerAnim.refresh();
    }

    public void setCycleTime(float v) {
        timerAnim.setCycleTime(v);
    }

    public void blink() {
        blink = true;
        endBlinkTime = System.currentTimeMillis();
    }

    public void unBlink() {
        blink = false;
        startBlinkTime = 0;
        BLINK_MULTIPLIER = 1f;
    }

    public boolean isBlink() {
        return blink;
    }
}
