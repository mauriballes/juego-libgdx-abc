package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.losdelcallejon.gamesmachine.ActionResolver;

/**
 * Created by HP on 09/12/2016.
 */
public class MenuScreen extends BaseScreen {

    /*
    *                           LEEME
    *   TODO AQUI APARECERAN LAS DISTINTAS NIVELES Y EL CABRON PODRA ELEGIR QUE JUGAR Y SI JUEGA SOLO O MULTIUSUARIO
    *
    * */
    private Image monoJugador;
    private Image multiJugador;
    private Image Unidad1;
    private Image Unidad2;
    private Stage interfazGrafica;
    boolean esMultijugador;
    int nivel;

    private String sexo;
    private ActionResolver actionResolver;

    public MenuScreen(AbcGameMain g, String sexo, final ActionResolver actionResolver) {
        super(g);
        this.sexo = sexo;
        this.actionResolver = actionResolver;
        esMultijugador=false;
        nivel=-1;
        this.interfazGrafica=new Stage(new FitViewport(512, 360));
        while(!game.getManager().update());
        monoJugador = new Image(game.getManager().get("monoplayer.jpg", Texture.class));
        multiJugador = new Image(game.getManager().get("multiplayer.png", Texture.class));
        Unidad1 = new Image(game.getManager().get("overfloor.png", Texture.class));
        Unidad2 = new Image(game.getManager().get("overfloor.png", Texture.class));
        verificarUnidadesDisponibles();
        Unidad2.setVisible(false);

        multiJugador.addListener(new InputListener()
        {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                esMultijugador=true;
                return true;
            }
        });
        monoJugador.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                esMultijugador=false;
                return true;
            }
        });
        Unidad1.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                nivel=0;
                createOptionsGameScreen();
                return true;

            }
        });
        Unidad2.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                nivel=1;
                createOptionsGameScreen();
                return true;
            }
        });
        monoJugador.setPosition(0, 360-monoJugador.getHeight());
        multiJugador.setPosition(multiJugador.getWidth(),360-monoJugador.getHeight());
        Unidad1.setPosition(50,10);
        Unidad2.setPosition(200+Unidad1.getWidth(),10);
        interfazGrafica.addActor(monoJugador);
        interfazGrafica.addActor(multiJugador);
        interfazGrafica.addActor(Unidad1);
        interfazGrafica.addActor(Unidad2);
    }

    private void verificarUnidadesDisponibles() {

    }

    private void createOptionsGameScreen() {
        OptionGameScreen optionGameScreen=new OptionGameScreen(game,esMultijugador,nivel,actionResolver);
        game.setScreen(optionGameScreen);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(interfazGrafica);
    }


    @Override
    public void render(float delta) {
        if (this.sexo.equals("M")){
            Gdx.gl.glClearColor(0f,0f,1f,1f);
        }else{
            Gdx.gl.glClearColor(1f,0.43f,0.78f,1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        interfazGrafica.act();
        interfazGrafica.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        interfazGrafica.dispose();
    }
}
