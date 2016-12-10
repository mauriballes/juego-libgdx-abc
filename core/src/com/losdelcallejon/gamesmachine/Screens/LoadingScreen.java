package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;

/**
 * Created by HP on 09/12/2016.
 */
public class LoadingScreen extends BaseScreen {
    private Stage stage;

    private Skin skin;

    private Label loading;


    public LoadingScreen(AbcGameMain g) {
        super(g);
        stage=new Stage(new FitViewport(640,360));
        skin=new Skin(Gdx.files.internal("skin/uiskin.json"));
        loading=new Label("Loading...",skin);
        loading.setPosition(320-loading.getWidth()/2,180-loading.getHeight()/2);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getManager().update())
        {
            game.goToLogin();
        }else
        {
            loading.setText("Loading... "+(game.getManager().getProgress()*100)+ "%");
        }

        stage.act();
        stage.draw();
    }
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
