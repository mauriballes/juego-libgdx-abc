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
import com.losdelcallejon.gamesmachine.Models.MCursados;
import com.losdelcallejon.gamesmachine.Models.MUnidades;


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
    private Stage interfazGrafica;
    boolean esMultijugador;
    int nivel;
    boolean primerPantalla;
    private Stage interfazGraficaDos;
    java.util.List<MUnidades> listUnidades;
    java.util.List<Integer> listCursadosId;

    private String sexo;
    private ActionResolver actionResolver;

    public MenuScreen(AbcGameMain g, String sexo, final ActionResolver actionResolver) {
        super(g);
        this.primerPantalla = true;
        this.sexo = sexo;
        this.actionResolver = actionResolver;
        this.listUnidades = this.actionResolver.obtenerListUnidades();
        this.listCursadosId = this.actionResolver.obtenerUnidadesDelUsuario();
        esMultijugador = false;
        nivel = -1;
        this.interfazGrafica = new Stage(new FitViewport(512, 360));
        this.interfazGraficaDos = new Stage(new FitViewport(512, 360));
        cargarUnidades();

        monoJugador = new Image(game.getManager().get("monoplayer.jpg", Texture.class));
        multiJugador = new Image(game.getManager().get("multiplayer.png", Texture.class));

        multiJugador.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                createSegundaPantalla(true);
                return true;
            }
        });
        monoJugador.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                createSegundaPantalla(false);
                return true;
            }
        });
        monoJugador.setPosition(0, 360 - monoJugador.getHeight());
        multiJugador.setPosition(multiJugador.getWidth(), 360 - monoJugador.getHeight());
        interfazGrafica.addActor(monoJugador);
        interfazGrafica.addActor(multiJugador);
    }

    private void cargarUnidades() {
        int separadorAncho = 40;
        int separadorLargo = 20;
        int fila = 1;
        int columna = 0;
        MUnidades mUnidadAnt = null;
        for (int i = 0; i < this.listUnidades.size(); i++) {
            final MUnidades mUnidad = this.listUnidades.get(i);
            Image Unidad = new Image(game.getManager().get("overfloor"+(mUnidad.getNivel())+".png", Texture.class));
            if (i == 0) {
                Unidad.setPosition(
                        0,
                        360 - (fila * (Unidad.getHeight() + separadorLargo) )
                );
                fila++;
                mUnidadAnt = mUnidad;
            } else {
                Unidad.setPosition(
                        columna * (Unidad.getWidth() + separadorAncho),
                        360 - (fila * (Unidad.getHeight() + separadorLargo) )
                );
                columna++;
                if ((i % 3) == 0) {
                    fila++;
                    columna = 0;
                }
                mUnidadAnt = mUnidad;
            }

            Unidad.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actionResolver.showToast("HAS SELECCIONADO " + mUnidad.getNombre() +" "
                            + mUnidad.getDescripcion()+" id= " + mUnidad.getId() + " nivel= "+ mUnidad.getNivel(),5000);
                    nivel = mUnidad.getNivel();
                    createOptionsGameScreen();
                    return true;
                }
            });
            if(i != 0){
//                Unidad.setVisible(listCursadosId.contains(mUnidadAnt.getId()));
            }
            interfazGraficaDos.addActor(Unidad);
        }
    }

    private void createSegundaPantalla(boolean esMultijugador) {
        this.esMultijugador = esMultijugador;
        primerPantalla = false;
        Gdx.input.setInputProcessor(interfazGraficaDos);
    }

    private void createOptionsGameScreen() {
        OptionGameScreen optionGameScreen = new OptionGameScreen(game, esMultijugador, nivel, actionResolver,sexo);
        game.setScreen(optionGameScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(interfazGrafica);
    }


    @Override
    public void render(float delta) {
        if (this.sexo.equals("M")) {
            Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(1f, 0.43f, 0.78f, 1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (primerPantalla) {
            interfazGrafica.act();
            interfazGrafica.draw();
        } else {
            interfazGraficaDos.act();
            interfazGraficaDos.draw();
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        interfazGrafica.dispose();
        interfazGraficaDos.dispose();

    }
}
