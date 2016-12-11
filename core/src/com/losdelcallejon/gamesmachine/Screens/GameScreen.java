package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
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

import io.socket.client.Socket;

/**
 * Created by HP on 09/12/2016.
 */
public class GameScreen extends BaseScreen {
    public static int nivel;
    public static boolean isMultiplayer;
    public static boolean esCreador;
    HashMap<String,String> Abecedario;
    private String palabra;
    private ArrayList<Letra> letraList;



    private Stage stage;
    private World world;
    private AbcController abcController;
    private Label puntaje;
    private Image cargando;
    private Stage stageCargando;
    private Skin skin;
    public GameScreen(AbcGameMain g,int nivel,boolean isMultiPlayer,boolean esCreador) {
            super(g);

                this.nivel = nivel;
                this.isMultiplayer = isMultiPlayer;
                this.esCreador = esCreador;
                Abecedario = Constants.OBTENER_ABECEDARIO();
                this.stage = new Stage(new FitViewport(640, 360));
                this.world = new World(new Vector2(0, -8), true);
                palabra = "";
                skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
                puntaje = new Label(palabra, skin);
                puntaje.setSize(200, 80);
                puntaje.setPosition(0, 0);
                this.stage.addActor(puntaje);
                stageCargando();
                this.letraList = new ArrayList<Letra>();
                abcController = new AbcController(Abecedario, palabra, letraList, stage, world, game.getManager());
                registrarEventos();
    }

    private void registrarEventos() {
        Socket socket =game.socket;
        /// Ya sea mono o multijugador necesitamos tener la primer palabra
        /// asi que debemos registrar el evento que pueda generar el servidor para mandarnos la palabra
        socket.on(Constants.GET_NEXT_PALABRA,this);
        //decirle al servidor que mande nueva palabra
        if(!isMultiplayer) //Todo registrar eventos que vaya necesitar el mono jugador
        {

        }else
        {
            // Cuando el oponente envie la tecla que pulso nosotros tenemos que eliminarla tambien de nuestra pantalla
            socket.on(Constants.LETRA_PULSADA,this);

            if(!esCreador) /// Si yo soy el retado
            {
                //Tengo que renderizar constantemente todas las letras que me envie mi retador, por que el es el creador de la partida
                socket.on(Constants.RENDERIZAR_LETRAS_POSICIONES,this);
            }
        }
    }

    public void stageCargando()
    {
        this.stageCargando=new Stage(new FitViewport(640, 360));
        cargando = new Image(game.getManager().get("logo.png", Texture.class));
        cargando.setPosition(440 - cargando.getWidth() / 2, 320 - cargando.getHeight());
        stageCargando.addActor(cargando);
    }
    @Override
    public void show() {
        if(!isMultiplayer){         // Si no es multijugador
            //TODO todavia no implementado la parte del mono jugador
            abcController.cargarRecursosDeLetras(abcController.generarLetras());
        }else       // Si es multijugador hay que ver si es creador de partida o retado
        {
            if(esCreador) ///Si es el creador de la partida genera los recursos y se lo envia al servidor para que este se lo envie al retador
            {
                abcController.cargarRecursosDeLetras(abcController.generarLetras());
                //TODO enviar al servidor las letras generadas
            }
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!palabra.equals("")){ // Si hay palabra, el juego continua en mono o multi jugador
            if(!isMultiplayer){     // Si es monojugador

            }else   // Si es multijugador
            {
                if(esCreador) // Como yo soy el creador es mi responsabilidad actualizar la pantalla para mi oponente
                {
                    abcController.validarCreador(game.socket);
                    if(abcController.letrasVacias())
                    {

                        abcController.cargarRecursosDeLetras(abcController.generarLetras());
                    }
                    //Envia las posiciones al servidor para que este se la mande a mi oponente
                    game.socket.emit(Constants.RENDERIZAR_LETRAS_POSICIONES,abcController.getLetraList());
                }else
                {
                    abcController.validarReceptor(game.socket);
                }
                puntaje.setText(abcController.getMiPuntaje());
                abcController.pintar(delta);
            }
        }else  // Si no hay palabra, hay que mostrar una pantalla de carga a la espera de que el servidor envie una o que diga si ya acabo el juego
        {
            stageCargando.act();
            stageCargando.draw();
        }
    }


    @Override
    public void hide() {
        abcController.hide();
    }

    @Override
    public void dispose() {
        abcController.dispose();
    }

    @Override
    public void call(Object... args) {
       Integer id= (Integer) args[0];
        switch (id)
        {
            case Constants.GET_NEXT_PALABRA_RESPUESTA:
                if(isMultiplayer)
                {

                }else
                {

                }
                break;
            case Constants.LETRA_PULSADA_RESPUESTA:
                break;
            case Constants.RENDERIZAR_LETRAS_POSICIONES_RESPUESTA:
                break;
        }
    }

}
