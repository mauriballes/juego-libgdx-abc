package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.InputControllers.AbcController;
import com.losdelcallejon.gamesmachine.InputControllers.ControlVirtual;
import com.losdelcallejon.gamesmachine.Actores.Letra;
import com.losdelcallejon.gamesmachine.InputControllers.ProcesadorEntrada;
import com.losdelcallejon.gamesmachine.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by HP on 09/12/2016.
 */
public class GameScreen extends BaseScreen {
    public static int nivel;
    public static boolean isMultiplayer;
    HashMap<String,String> Abecedario;
    private String palabra;
    private ArrayList<Letra> letraList;



    private Stage stage;
    private World world;
    private AbcController abcController;

    public GameScreen(AbcGameMain g,int nivel,boolean isMultiPlayer) {
        super(g);
        this.nivel=nivel;
        this.isMultiplayer=isMultiPlayer;
        Abecedario=Constants.OBTENER_ABECEDARIO();
        // OBTENER LA PRIMER PALABRA DEL NIVEL DADO Y ESPERAR LA RESPUESTA DEL SERVIDOR
        //Object parametros=new Object();
        //game.socket.emit(Constants.GET_PALABRA_NIVEL,parametros);
        palabra="SAMUEL";
        this.stage=new Stage(new FitViewport(640,360));
        this.world=new World(new Vector2(0,-12),true);
        abcController=new AbcController(Abecedario,palabra,letraList,stage,world,game.getManager());

    }

    @Override
    public void show() {

        abcController.generarLetras();
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        world.step(delta,6,2);
        stage.draw();
    }


    @Override
    public void hide() {
        stage.clear();
        for(Letra letrita: letraList)
        {
            letrita.detach();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    @Override
    public void call(Object... args) {
       Integer id= (Integer) args[0];
        switch (id)
        {
            case Constants.GET_PALABRA_NIVEL_RESPUESTA:
                ///Todo Procesar palabra en la pantalla del juego
                break;
            case Constants.UPDATE_OPONENTE_RESPUESTA:
                //Todo actualizar oponente
                break;
        }
    }

}
