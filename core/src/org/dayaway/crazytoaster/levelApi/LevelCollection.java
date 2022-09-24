package org.dayaway.crazytoaster.levelApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;


//Коллекция уровней
public class LevelCollection {

    private static final LevelCollection instance = new LevelCollection();

    private final List<LevelStatic> levelList;

    private final Preferences pref;

    {
        levelList = new ArrayList<>();

        pref = Gdx.app.getPreferences("crazy toaster preferences");
        putPref();

        //LEVEL_1
        levelList.add(new LevelStatic(0, pref.getBoolean("level_1_open"), pref.getBoolean("level_1_ending")));
        levelList.get(0).add(ELevel.FIVE);
        levelList.get(0).add(ELevel.FIVE);
        levelList.get(0).add(ELevel.FIVE);
        levelList.get(0).add(ELevel.FIVE);
        levelList.get(0).add(ELevel.FIVE);

        //LEVEL_2
        levelList.add(new LevelStatic(1, pref.getBoolean("level_2_open"), pref.getBoolean("level_2_ending")));
        levelList.get(1).add(ELevel.FIVE);
        levelList.get(1).add(ELevel.FIVE);
        levelList.get(1).add(ELevel.THREE);
        levelList.get(1).add(ELevel.FIVE);
        levelList.get(1).add(ELevel.FIVE);
        levelList.get(1).add(ELevel.THREE);

        //LEVEL_3
        levelList.add(new LevelStatic(2, pref.getBoolean("level_3_open"), pref.getBoolean("level_3_ending")));
        levelList.get(2).add(ELevel.FIVE);
        levelList.get(2).add(ELevel.FIVE);
        levelList.get(2).add(ELevel.THREE);
        levelList.get(2).add(ELevel.THREE);
        levelList.get(2).add(ELevel.FIVE);
        levelList.get(2).add(ELevel.THREE);
        levelList.get(2).add(ELevel.ONE);

        //LEVEL_4
        levelList.add(new LevelStatic(3, pref.getBoolean("level_4_open"), pref.getBoolean("level_4_ending")));
        levelList.get(3).add(ELevel.FIVE);
        levelList.get(3).add(ELevel.FIVE);
        levelList.get(3).add(ELevel.ONE);
        levelList.get(3).add(ELevel.FIVE);
        levelList.get(3).add(ELevel.THREE);
        levelList.get(3).add(ELevel.THREE);
        levelList.get(3).add(ELevel.THREE);
        levelList.get(3).add(ELevel.FIVE);
        levelList.get(3).add(ELevel.ONE);
        levelList.get(3).add(ELevel.FIVE);

        //LEVEL_5
        levelList.add(new LevelStatic(4, pref.getBoolean("level_5_open"), pref.getBoolean("level_5_ending")));
        levelList.get(4).add(ELevel.FIVE);
        levelList.get(4).add(ELevel.ONE);
        levelList.get(4).add(ELevel.THREE);
        levelList.get(4).add(ELevel.FIVE);
        levelList.get(4).add(ELevel.THREE);
        levelList.get(4).add(ELevel.ONE);
        levelList.get(4).add(ELevel.FIVE);
        levelList.get(4).add(ELevel.THREE);
        levelList.get(4).add(ELevel.THREE);
        levelList.get(4).add(ELevel.BANANA);
        levelList.get(4).add(ELevel.FIVE);

        //LEVEL_6
        levelList.add(new LevelStatic(5, pref.getBoolean("level_6_open"), pref.getBoolean("level_6_ending")));
        levelList.get(5).add(ELevel.FIVE);
        levelList.get(5).add(ELevel.THREE);
        levelList.get(5).add(ELevel.BANANA);
        levelList.get(5).add(ELevel.THREE);
        levelList.get(5).add(ELevel.FIVE);
        levelList.get(5).add(ELevel.FIVE);
        levelList.get(5).add(ELevel.THREE);
        levelList.get(5).add(ELevel.BANANA);
        levelList.get(5).add(ELevel.FIVE);
        levelList.get(5).add(ELevel.THREE);
        levelList.get(5).add(ELevel.ONE);
        levelList.get(5).add(ELevel.BANANA);

        //LEVEL_7
        levelList.add(new LevelStatic(6, pref.getBoolean("level_7_open"), pref.getBoolean("level_7_ending")));
        levelList.get(6).add(ELevel.FIVE);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.FIVE);
        levelList.get(6).add(ELevel.FIVE);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.THREE);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.THREE);
        levelList.get(6).add(ELevel.BANANA);
        levelList.get(6).add(ELevel.THREE);

        //LEVEL_8
        levelList.add(new LevelStatic(7, pref.getBoolean("level_8_open"), pref.getBoolean("level_8_ending")));
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.THREE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.FIVE);
        levelList.get(7).add(ELevel.ONE);
        levelList.get(7).add(ELevel.CUCUMBER);
        levelList.get(7).add(ELevel.THREE);

        //LEVEL_9
        levelList.add(new LevelStatic(8, pref.getBoolean("level_9_open"), pref.getBoolean("level_9_ending")));
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.CUCUMBER);
        levelList.get(8).add(ELevel.BANANA);
        levelList.get(8).add(ELevel.THREE);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.THREE);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.ONE);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.CUCUMBER);
        levelList.get(8).add(ELevel.BANANA);
        levelList.get(8).add(ELevel.FIVE);
        levelList.get(8).add(ELevel.CUCUMBER);
        levelList.get(8).add(ELevel.THREE);
        levelList.get(8).add(ELevel.FIVE);

        //LEVEL_10
        levelList.add(new LevelStatic(9, pref.getBoolean("level_10_open"), pref.getBoolean("level_10_ending")));
        levelList.get(9).add(ELevel.FIVE);
        levelList.get(9).add(ELevel.BANANA);
        levelList.get(9).add(ELevel.FIVE);
        levelList.get(9).add(ELevel.BANANA);
        levelList.get(9).add(ELevel.FIVE);
        levelList.get(9).add(ELevel.CUCUMBER);
        levelList.get(9).add(ELevel.THREE);
        levelList.get(9).add(ELevel.FIVE);
        levelList.get(9).add(ELevel.ONE);
        levelList.get(9).add(ELevel.FIVE);
        levelList.get(9).add(ELevel.CUCUMBER);
        levelList.get(9).add(ELevel.THREE);
        levelList.get(9).add(ELevel.CUCUMBER);
        levelList.get(9).add(ELevel.THREE);
        levelList.get(9).add(ELevel.BANANA);
        levelList.get(9).add(ELevel.CUCUMBER);
        levelList.get(9).add(ELevel.ONE);
        levelList.get(9).add(ELevel.BANANA);

        //LEVEL_11
        levelList.add(new LevelStatic(10, pref.getBoolean("level_11_open"), pref.getBoolean("level_11_ending")));
        levelList.get(10).add(ELevel.FIVE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.ONE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.CUCUMBER);
        levelList.get(10).add(ELevel.THREE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.ONE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.THREE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.THREE);
        levelList.get(10).add(ELevel.THREE);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.CUCUMBER);
        levelList.get(10).add(ELevel.BANANA);
        levelList.get(10).add(ELevel.ONE);
        levelList.get(10).add(ELevel.FIVE);
        levelList.get(10).add(ELevel.THREE);

        //LEVEL_12
        levelList.add(new LevelStatic(11, pref.getBoolean("level_12_open"), pref.getBoolean("level_12_ending")));
        levelList.get(11).add(ELevel.FIVE);
        levelList.get(11).add(ELevel.ONE);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.ONE);
        levelList.get(11).add(ELevel.BANANA);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.BANANA);
        levelList.get(11).add(ELevel.FIVE);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.FIVE);
        levelList.get(11).add(ELevel.CUCUMBER);
        levelList.get(11).add(ELevel.BANANA);
        levelList.get(11).add(ELevel.ONE);
        levelList.get(11).add(ELevel.CUCUMBER);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.FIVE);
        levelList.get(11).add(ELevel.ONE);
        levelList.get(11).add(ELevel.BANANA);
        levelList.get(11).add(ELevel.BANANA);
        levelList.get(11).add(ELevel.THREE);
        levelList.get(11).add(ELevel.FIVE);
    }
    private LevelCollection() {
    }

    public static LevelCollection getInstance() {
        return instance;
    }

    public List<LevelStatic> get() {
        return levelList;
    }

    public void putPref() {

        if(!pref.contains("level_1_open")) {
            pref.putBoolean("level_1_open", true);
        }
        if(!pref.contains("level_2_open")) {
            pref.putBoolean("level_2_open", false);
        }
        if(!pref.contains("level_3_open")) {
            pref.putBoolean("level_3_open", false);
        }
        if(!pref.contains("level_4_open")) {
            pref.putBoolean("level_4_open", false);
        }
        if(!pref.contains("level_5_open")) {
            pref.putBoolean("level_5_open", false);
        }
        if(!pref.contains("level_6_open")) {
            pref.putBoolean("level_6_open", false);
        }
        if(!pref.contains("level_7_open")) {
            pref.putBoolean("level_7_open", false);
        }
        if(!pref.contains("level_8_open")) {
            pref.putBoolean("level_8_open", false);
        }
        if(!pref.contains("level_9_open")) {
            pref.putBoolean("level_9_open", false);
        }
        if(!pref.contains("level_10_open")) {
            pref.putBoolean("level_10_open", false);
        }
        if(!pref.contains("level_11_open")) {
            pref.putBoolean("level_11_open", false);
        }
        if(!pref.contains("level_12_open")) {
            pref.putBoolean("level_12_open", false);
        }



        if(!pref.contains("level_1_ending")) {
            pref.putBoolean("level_1_ending", false);
        }
        if(!pref.contains("level_2_ending")) {
            pref.putBoolean("level_2_ending", false);
        }
        if(!pref.contains("level_3_ending")) {
            pref.putBoolean("level_3_ending", false);
        }
        if(!pref.contains("level_4_ending")) {
            pref.putBoolean("level_4_ending", false);
        }
        if(!pref.contains("level_5_ending")) {
            pref.putBoolean("level_5_ending", false);
        }
        if(!pref.contains("level_6_ending")) {
            pref.putBoolean("level_6_ending", false);
        }
        if(!pref.contains("level_7_ending")) {
            pref.putBoolean("level_7_ending", false);
        }
        if(!pref.contains("level_8_ending")) {
            pref.putBoolean("level_8_ending", false);
        }
        if(!pref.contains("level_9_ending")) {
            pref.putBoolean("level_9_ending", false);
        }
        if(!pref.contains("level_10_ending")) {
            pref.putBoolean("level_10_ending", false);
        }
        if(!pref.contains("level_11_ending")) {
            pref.putBoolean("level_11_ending", false);
        }
        if(!pref.contains("level_12_ending")) {
            pref.putBoolean("level_12_ending", false);
        }

        pref.flush();

    }

}
