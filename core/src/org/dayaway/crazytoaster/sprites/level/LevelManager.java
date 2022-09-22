package org.dayaway.crazytoaster.sprites.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import org.dayaway.crazytoaster.sprites.Toaster;
import org.dayaway.crazytoaster.states.PlayState;

import java.util.Random;

public class LevelManager {

    private final PlayState playState;
    
    private final int LEVEL_COUNTS = 4;

    public static final int LEVEL_GAP = 288;
    public static final int INDENT = 19;

    private int focusLevelIndex;
    private int previousLevelIndex;

    
    private int countLevel = 0;
 
    private final Array<Level> levels;

    private final Random random = new Random();

    private boolean onePrevious = false;

    private boolean shake = false;

    private long timeStartShake;

    public LevelManager (PlayState playState) {
        this.playState = playState;
        this.levels = new Array<>();
        
        while (countLevel < LEVEL_COUNTS) {
            levels.add(getLevel());
            countLevel++;
        }

        focusLevelIndex = 0;
        previousLevelIndex = LEVEL_COUNTS - 1;
    }

    public void shake() {
        timeStartShake = System.currentTimeMillis();
        shake = true;
    }

    public void update(float dt) {
        for (Level level : levels) {
            level.update(dt);
            if(shake) {
                if((System.currentTimeMillis() - timeStartShake) < 300) {
                    level.getFloor().shake();
                }
                else {
                    shake = false;
                    playState.turnStart();
                    if(playState.getToast().isLost()) {
                        playState.setGAME_OVER(true);
                    }
                }
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        for (int i = 0; i < LEVEL_COUNTS; i++) {
            if(!playState.getGSM().isFirstScreen()) {
                levels.get(i).render(batch);
            } else {
                if (i != 0) {
                    levels.get(i).render(batch);
                }
            }
        }
    }
    public void dispose() {
        for (Level level : levels) {
            level.dispose();
        }
    }

    
    public Toaster getFullToaster() {
        for (Level level : levels) {
            if(level.getToaster().isFull()){
                return level.getToaster();
            }
        }
        return null;
    }

    public Toaster getFocusToaster() {
        return levels.get(focusLevelIndex).getToaster();
    }

    public Toaster getPreviousToaster() {
        return levels.get(previousLevelIndex).getToaster();
    }

    public Toaster nextToaster() {
        if (focusLevelIndex != LEVEL_COUNTS - 1) {
            previousLevelIndex = focusLevelIndex++;
        } else {
            focusLevelIndex = 0;
            previousLevelIndex = LEVEL_COUNTS - 1;
        }

        return levels.get(focusLevelIndex).getToaster();
    }

    public void reposition() {
        levels.get(previousLevelIndex).dispose();
        levels.set(previousLevelIndex, getLevel());
        countLevel++;
    }

    public Level getLevel() {

        while (true) {
            Array<ELevel> eLevelArray = new Array<>();
            eLevelArray.add(ELevel.FIVE);

            if (countLevel > 2) {
                eLevelArray.add(ELevel.THREE);
            }
            if (countLevel > 4) {
                eLevelArray.add(ELevel.BANANA);
            }
            if (countLevel > 6) {
                eLevelArray.add(ELevel.CUCUMBER);
            }
            if (countLevel > 8) {
                eLevelArray.add(ELevel.ONE);
            }

            int percent = 0;

            for (ELevel eLevel : eLevelArray) {
                percent += eLevel.getChance();
            }

            int result = random.nextInt(percent) + 1;

            if (result <= 1) {
                onePrevious = false;
                return new LevelFivePlatform(countLevel);
            } else if (result <= 2) {
                onePrevious = false;
                return new LevelThreePlatform(countLevel);
            } else if (result <= 3) {
                onePrevious = false;
                return new LevelBananaPlatform(countLevel);
            } else if (result <= 4) {
                onePrevious = false;
                return new LevelCucumberPlatform(countLevel, playState);
            } else if (result <= 5 && !onePrevious) {
                onePrevious = true;
                return new LevelOnePlatform(countLevel);
            }
        }

    }

    public Level getThisLevel() {
        return levels.get(focusLevelIndex);
    }

    //Если посмотрели рекламу за вознаграждение меняем фокус на предыдущий
    public void restartForReward() {
        focusLevelIndex = previousLevelIndex;

        if(previousLevelIndex != 0) {
            previousLevelIndex--;
        } else {
            previousLevelIndex = LEVEL_COUNTS-1;
        }
        levels.get(focusLevelIndex).getToaster().restartForReward();
    }

    public enum ELevel {
        FIVE(1),
        THREE(1),
        BANANA(1),
        CUCUMBER(1),
        ONE(1);

        private final int chance;

        ELevel(int chance) {
            this.chance = chance;
        }

        public int getChance() {
            return chance;
        }


    }

}
