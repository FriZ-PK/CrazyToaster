package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    //Максимальное вреимя отображения фрейма
    private float maxFrameTime;
    //Максимальное вреимя отображения фрейма в инверсии
    private float inverseMaxFrameTime;
    //Текущее время отображения фрейма
    private float currentFrameTime;
    //Число фреймов
    private final int frameCount;
    //Текущий фрейм
    private int frame;

    private final float cycleTime;

    private boolean INVERSE = false;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0,frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        this.cycleTime = cycleTime;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if(!INVERSE) {
            if (currentFrameTime > maxFrameTime) {
                frame++;
                currentFrameTime = 0;
            }
            if (frame >= frameCount) {
                frame = 0;
            }
        }
        else {
            if (currentFrameTime > inverseMaxFrameTime) {
                frame--;
                currentFrameTime = 0;
            }
            if (frame < 0) {
                frame = frameCount-1;
            }
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public int getCurrentFrameCount() {
        return frame;
    }

    public void refresh() {
        frame = 0;
    }
    public void refresh(int i) {
        frame = i;
    }

    public void inverse() {
        INVERSE = !INVERSE;
        inverseMaxFrameTime = cycleTime / frameCount;
    }

    public void inverse(float cycleTime) {
        INVERSE = !INVERSE;
        inverseMaxFrameTime = cycleTime / frameCount;
    }

    public boolean isINVERSE() {
        return INVERSE;
    }

    public void setCycleTime(float cycleTime) {
        maxFrameTime = cycleTime / frameCount;
    }
}
