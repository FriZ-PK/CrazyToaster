package org.dayaway.crazytoaster.levelApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;

//Уровень для Коллекции уровней
public class LevelStatic implements ILevel{

    private final List<ELevel> levelList;

    private final int id;

    private boolean open;
    private boolean ending;

    public LevelStatic (int id, boolean open, boolean ending) {
        levelList = new ArrayList<>();
        this.id = id;
        this.open = open;
        this.ending = ending;
    }
    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isEnding() {
        return ending;
    }

    @Override
    public List<ELevel> get() {
        return levelList;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void add(ELevel eLevel) {
        levelList.add(eLevel);
    }

    public void ending() {
        Preferences pref = Gdx.app.getPreferences("crazy toaster preferences");
        pref.putBoolean("level_" + (id+1) + "_ending", true);
        pref.flush();
        this.ending = true;
    }

    public void open() {
        Preferences pref = Gdx.app.getPreferences("crazy toaster preferences");
        pref.putBoolean("level_" + (id+1) + "_open", true);
        pref.flush();
        this.open = true;
    }
}
