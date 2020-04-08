package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Miguel on 3/12/2019.
 * Hud class:
 *      We handle all data that its printed in screen
 * Implements Disposable
 */
public class Hud implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //PoopyBird score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    // Player's Name
    private String playerName;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label playerLabel;

    public Hud(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 200;
        timeCount = 0;
        score = 0;

        // We get this data from properties file
        playerName = "ROBOTEC";

        //setup the HUD viewport using a new camera seperate from our main cam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        playerLabel = new Label(playerName, new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
        //update time count
        timeCount += dt;

        if(timeCount >= 1){
            //we play if the time is plus 0
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                //Time is Over - Finish the game
                timeUp = true;
            }
            //Update the label name
            countdownLabel.setText(String.format("%03d", worldTimer));
            //timeCount is zero and prepare for next uptdate call
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public boolean isTimeUp() { return timeUp; }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
