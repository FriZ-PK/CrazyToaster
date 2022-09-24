package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.dayaway.crazytoaster.sprites.Toaster;

public class ToasterAnimationManager {

    private Animation toasterAnimation;
    private final Toaster toaster;
    private final AnimationPack pack;


    public ToasterAnimationManager(Toaster toaster, AnimationPack pack) {
        this.toaster = toaster;
        this.pack = pack;
        setDirection();
    }


    public void dispose() {
        //pack.dispose();
    }

    public TextureRegion getFrame() {
        return toasterAnimation.getFrame();
    }

    public void update(float dt) {
        toasterAnimation.update(dt);

        //Если тостер сытый, нужно ,больше контроля
        if(toaster.isFull()) {

            //Если анмация поедания, нужно проиграть один раз
            if(toaster.isEat() && !toaster.isShot()) {
                //8 - последний фрейм
                if(toasterAnimation.getCurrentFrameCount() == 8) {
                    //Тостер сыт
                    toaster.wellFed();
                }
            }

            if(toaster.isShot()) {
                toaster.release();
            }

        }

    }

    public void setDirection() {
        //Если пустой тостер
        if(!toaster.isFull()) {
            toasterAnimation = new Animation(toaster.getDirection() ? pack.getEmptyToasterRight():pack.getEmptyToasterLeft(),
                    8, 0.5f);
        }
        //Если с едой
        else {
            toasterAnimation = new Animation(toaster.getDirection() ? pack.getFullToasterRight(): pack.getFullToasterLeft(),
                    8, 0.5f);
        }
    }

    public void eating() {
        toasterAnimation = new Animation(toaster.getDirection() ? pack.getEatingToasterRight(): pack.getEatingToasterLeft(),
                9, 0.5f);
    }
}
