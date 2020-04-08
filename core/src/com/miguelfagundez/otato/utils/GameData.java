package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Miguel on 3/12/2019.
 * Class: GameData
 * This class will handle all game's settings data and basic values
 */
public class GameData {

    private String PREFS_NAME = Constants.GAME_NAME + "Settings";
    private Preferences prefs = null;
    protected boolean isEmpty;

    /**
     * Method: GameData
     * Definition: Constructor
     * Parameters:
     *      None
     */
    public GameData(){

        prefs = getPrefs();

        isEmpty = (!prefs.get().isEmpty());

        if (!prefs.contains("language")) {
            prefs.putInteger("language", 0);
            prefs.flush();
        }
        if (!prefs.contains("player")) {
            prefs.putString("player", "default");
            prefs.flush();
        }
        if (!prefs.contains("mode")) {
            prefs.putInteger("mode", 0);
            prefs.flush();
        }
        if (!prefs.contains("music")) {
            prefs.putBoolean("music", true);
            prefs.flush();
        }
        if (!prefs.contains("sounds")) {
            prefs.putBoolean("sounds", true);
            prefs.flush();
        }
        if (!prefs.contains("alerts")) {
            prefs.putBoolean("alerts", true);
            prefs.flush();
        }
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
            prefs.flush();
        }
    }

    protected Preferences getPrefs() {
        if(prefs==null){
            prefs = Gdx.app.getPreferences(PREFS_NAME);
        }
        return prefs;
    }

    public String getPlayer(String key){
        return prefs.getString(key, "default");
    }

    public int getInteger(String key){
        return prefs.getInteger(key, 0);
    }

    public boolean getSoundEffects(String key){ return prefs.getBoolean(key, true); }

    public void putString(String key, String value){
        prefs.putString(key, value);
        prefs.flush();
    }

    public void putSoundEffects(String key, boolean value){
        prefs.putBoolean(key, value);
        prefs.flush();
    }

    public void putInteger(String key, int value){
        prefs.putInteger(key, value);
        prefs.flush();
    }

    public void dispose(){
        prefs.clear();
    }
}
