package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;

/**
 * Created by HP on 11/12/2016.
 */

public class OptionGameScreen extends BaseScreen{
    boolean esMultijugador;
    int nivel;
    boolean buscando;
    private Image buscandoImg;
    private Stage interfazBuscando;
    private Image aJugar;
    private Stage interfazGrafica;

    public OptionGameScreen(AbcGameMain g,boolean esMultijugador,int nivel) {
        super(g);
        this.esMultijugador=esMultijugador;
        this.nivel=nivel;
        buscando=false;
        this.interfazGrafica=new Stage(new FitViewport(640, 360));
        this.interfazBuscando=new Stage(new FitViewport(640, 360));
        buscandoImg = new Image(game.getManager().get("buscando.jpg", Texture.class));
        aJugar = new Image(game.getManager().get("overfloor.png", Texture.class));
        aJugar.setPosition(50,10);
        buscandoImg.setPosition(0,0);
        interfazGrafica.addActor(aJugar);
        interfazBuscando.addActor(buscandoImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!buscando)
        {
            interfazGrafica.act();
            interfazGrafica.draw();
        }else
        {
            interfazBuscando.act();
            interfazBuscando.draw();
        }
    }

    @Override
    public void call(Object... args) {
        super.call(args);
    }
}
